package netty.demo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.PlatformDependent;
import netty.demo.common.NativeSupport;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ThreadFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @description: 抽象服务类
 * @author: FS
 * @date: 2018-08-14 10:25
 */
public abstract class AbstractSocketServer implements SocketServer {

    protected SocketAddress port;

    protected EventLoopGroup boss;

    protected EventLoopGroup worker;

    protected Class<? extends ServerChannel> serverChannelClass;

    protected ServerBootstrap bootstrap;

    public AbstractSocketServer(int port) {
        this.port = new InetSocketAddress(port);
        this.boss = getEventLoopGroup(1, r -> new Thread(r, "server.boss"));
        this.worker = getEventLoopGroup(getAvailableWorkerThreads(), r -> new Thread(r, "server.worker"));
        this.serverChannelClass = getServerChannelClass();
        this.init();
    }

    @Override
    public void run() {
        try {
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            System.out.println("服务正在监听：" + port + "端口");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            shutdown();
            System.out.println("服务正在已关闭");
        }
    }

    @Override
    public void shutdown() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }

    /**
     * 初始化
     */
    protected void init() {
        bootstrap = new ServerBootstrap();

        bootstrap.group(boss, worker).channel(serverChannelClass);

        //
        // 使用池化的directBuffer
        // 一般高性能的场景下,使用的堆外内存，也就是直接内存，使用堆外内存的好处就是减少内存的拷贝，和上下文的切换
        // 缺点是堆外内存处理的不好容易发生堆外内存OOM当然也要看当前的JVM是否只是使用堆外内存
        // 换而言之就是是否能够获取到Unsafe对象#PlatformDependent.directBufferPreferred()
        //
        bootstrap.childOption(ChannelOption.ALLOCATOR,
                new PooledByteBufAllocator(PlatformDependent.directBufferPreferred()));
    }

    /**
     * 获取eventLoopGroup
     *
     * @param threads 线程数
     * @param factory ThreadFactory
     * @return EventLoopGroup
     */
    public EventLoopGroup getEventLoopGroup(int threads, ThreadFactory factory) {
        return NativeSupport.isSupportNativeET()
                ? new EpollEventLoopGroup(threads, factory)
                : new NioEventLoopGroup(threads, factory);
    }

    /**
     * 获取 SocketChannel class
     *
     * @return class<? extends ServerChannel>
     */
    public Class<? extends ServerChannel> getServerChannelClass() {
        return NativeSupport.isSupportNativeET()
                ? EpollServerSocketChannel.class
                : NioServerSocketChannel.class;
    }

    /**
     * 获取可用的线程数
     *
     * @return
     */
    protected int getAvailableWorkerThreads() {
        return Runtime.getRuntime().availableProcessors();
    }

    public SocketAddress getPort() {
        return port;
    }

    public EventLoopGroup getBoss() {
        return boss;
    }

    public EventLoopGroup getWorker() {
        return worker;
    }
}
