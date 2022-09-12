package ex02;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/memberSV")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MemberServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		MemberDAO dao = new MemberDAO();
		List<MemberVO> voList = new ArrayList<MemberVO>();		
		
		//html���� ���� ���� �޾ƿ´�.
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		String command = request.getParameter("command");

		//�߰��� ���
		if(command != null) {
			if(command.equals("addMember")) {
				MemberVO vo = new MemberVO();
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				
				dao.addMember(vo);
			}else if(command.equals("delMember")) {
				dao.deleteMember(request.getParameter("delId"));
			}
			
		}
		
		//ArrayList�� List�� ���� �˾ƿ���!
		voList = dao.listMember();
		String str = ("<html><body><table border='1'>");
		str += "<tr><td>���̵�</td><td>��й�ȣ</td><td>�̸�</td><td>�̸���</td><td>������ư</td></tr>";
		for(MemberVO vo : voList) {
			str += "<tr>";
			str += "<td>"+vo.getId()+"</td>";
			str += "<td>"+vo.getPwd()+"</td>";
			str += "<td>"+vo.getName()+"</td>";
			str += "<td>"+vo.getEmail()+"</td>";
			str += "<td>"+"<a href='memberSV?command=delMember&delId="+vo.getId()+"'>����</a>"+"</td>";
			str += "</tr>";
		}
		str += ("</table></body></html>");
		
		out.print(str);
	}
}
