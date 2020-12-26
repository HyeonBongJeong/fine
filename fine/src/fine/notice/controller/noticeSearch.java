package fine.notice.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fine.notice.model.NoticeVO;
import fine.notice.service.NoticeService;

/**
 * Servlet implementation class noticeSearch
 */
@WebServlet("/noticeSearch.do")
public class noticeSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public noticeSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String word = request.getParameter("title");
		NoticeService nService = new NoticeService();
			int pageSize = 10; // 페이지 당 글 수
			int pageBlock = 10; // 페이지 링크 수
			try {			
					//총 글 개수 
				int nCount = nService.getSearchBoardCount(word); /*변경 : 메소드*/
				System.out.println(nCount);
					//페이지 수 초기화
				String pageNum = request.getParameter("pageNum");
				if (pageNum == null) {
					pageNum = "1";
				} else if(pageNum.equals("")){
					pageNum = "1";
				}
					//startPage , endPage 구하는 식
				int currentPage = 1;
				try {
					currentPage = Integer.parseInt(pageNum);
				} catch(Exception e) {
					e.printStackTrace();
					
				}
				
				int pageCount = (nCount / pageSize) + (nCount % pageSize == 0 ? 0 : 1);
				int startPage = 1;
				int endPage = 1;
				if (currentPage % pageBlock == 0) {
					startPage = ((currentPage / pageBlock) - 1) * pageBlock + 1;
				} else {
					startPage = ((currentPage / pageBlock)) * pageBlock + 1;
				}
				endPage = startPage + pageBlock - 1;
				if (endPage > pageCount)
					endPage = pageCount;

					// 페이징 rownum 구하기
				int startRnum = ((currentPage-1)*pageSize)+1;	// 거의 공식
				int endRnum = startRnum + pageSize - 1;			// currentPage*pageSize
				System.out.println(startRnum +  " - "+ endRnum );
				List<NoticeVO> list = nService.searchNotice(word, startRnum, endRnum);  /*변경 : 메소드*/

					//보내주기 
				request.setAttribute("startPage", startPage);  
				request.setAttribute("endPage", endPage);
				request.setAttribute("PageNum", currentPage);
				request.setAttribute("word", word);
				request.setAttribute("list", list);	/* 변경 : el태그 - jsp이랑 맞추기  */
				RequestDispatcher disp = request.getRequestDispatcher("/view/notice/noticeSearchList.jsp"); /*변경 : 경로*/
				disp.forward(request, response);
			
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
}
