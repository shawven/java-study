package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: FS
 * @date: 2018-07-23 15:29
 */
public class Test extends HttpServlet {

    private static final long serialVersionUID = -6014493559523799376L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        InputStream inputStream = req.getInputStream();

        byte[] bytes = new byte[1024];

        StringBuilder sb = new StringBuilder();


        int len;
        while ((len = inputStream.read(bytes, 0, bytes.length)) != -1) {
            sb.append(new String (bytes, 0, len));
        }

        byte[] rsp = sb.toString().getBytes();

        resp.getOutputStream().write(rsp, 0, rsp.length);
    }
}
