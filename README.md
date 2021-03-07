## 🌞 FINE 🌞
유기동물 입양 서비스 제공

## 목록 
1. [개요](#개요)
2. [기대효과](#기대효과)
3. [사용도구](#-사용도구)
4. [개발순서](#-개발순서)
5. [보완점](#-보완점)

##  ✨ 개요
유기견들이 많아지고 무분별한 펫샵의 새끼공장으로 인한 반려견의 학대 등등 많은 일이 일어나고 있는 현재 펫샵은 눈에 띄기 쉽게 그리고 다가가기 쉽게 인테리어 되어있고 체인점을 내는곳이 많아졌습니다. 
하지만 유기견을 보호하는 보호소는 몇몇 사람들에겐 좋지 않은 이미지를 가지고 있습니다.. 유기견이 들어오면 못나가고 안락사 당하는 곳이라고 생각하는 사람들이 많을 것입니다. 그리고 일반 펫샵은 강아지를 입양이라는 개념으로 대하는 것 이 아닌 판매라는 목적으로 반려견들의 견권을 바닥으로 떨어트리고 있습니다.
유기견 분양도 일반 펫샵처럼 찾아가기 쉽고 다가가기 편하다면 어떨까 생각을 하다가 일반 보호소들은 다 따로 자신들 만의 보호소를 운영하느라 각자의 홈페이지에서 입양,분양을 받는 다는 사실을 알게 되었고 유기견 분양 통합 플랫폼으로 사람들에게 다가간다면 자신의 지역에서 쉽고 간편하게 또 좀 더 다양한 시선으로 유기견을 볼거라 생각이 들어 기획하게 되었습니다.
## 기대효과
![11](https://user-images.githubusercontent.com/69295153/106466895-43a05e00-64df-11eb-8310-cacb2e494dfa.PNG)

##  🔧 사용도구
![22](https://user-images.githubusercontent.com/69295153/106466899-44d18b00-64df-11eb-9144-ca27739c153f.PNG)

#### 기타 라이브러리 / API

| 라이브러리                    |
| ------------------------------|
| jstl                          |
| JSON-simple                   |
| JdataPicker                   |
| cos                           |
| javaMail API                  |
| gson                          | 
| 공공데이터포털                | 
| 카카오 맵 API                 | 


## 👩‍💻 Member 

#### 오승하
- 공지사항 기능 전체(notice)
- 글 작성시 고정글 설정 선택가능, 최상단에 위치
- 파일업로드


##  🔧 개발순서
![Fine](https://user-images.githubusercontent.com/69295153/106555996-fa452280-6561-11eb-8402-275fefdcea22.jpg)

### DB 설계
![Copy of fine (1)](https://user-images.githubusercontent.com/69295153/106625198-cdbcf500-65b9-11eb-8d8d-5b2bf0f4b582.png)

### 테이블 기술서
![kh2조 semi_1](https://user-images.githubusercontent.com/69295153/106628659-6143f500-65bd-11eb-9410-fb32a3e1f782.jpg)
![kh2조 semi_2](https://user-images.githubusercontent.com/69295153/106628663-62752200-65bd-11eb-9b1d-0021e40a35b1.jpg)
![kh2조 semi_3](https://user-images.githubusercontent.com/69295153/106628665-630db880-65bd-11eb-9f7b-77147828e2a9.jpg)

### notice 클래스 다이어그램
![Untitled Diagram](https://user-images.githubusercontent.com/69295153/106564533-ae4daa00-6570-11eb-8ce7-e412ecd999c2.png)

### 주요소스 
#### 게시판 페이징
noticeList.jsp
```jsx
public int UpdateNotice(Connection conn, String title, String content, int no , List<String> img) {
		String sql = "update notice set notice_title = ?, notice_contents = ?" + " where notice_no = ?";
		int result = 0;
		int result2 = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, no);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		for (int i = 0; i < img.size(); i++) {
		
			sql = "update tbl_img set img = ? where notice_no = ? ";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, (String)img.get(i));
				pstmt.setInt(2, no);
				System.out.println(img);
				result2 = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs, pstmt);
			}
		}
		if (result != 0 && result2 != 0 ) 
			return 1;
			else return 0;
	}
```
NoticeDAO.java
```jsx
	// 공지사항 목록 페이징 - 공지사항 총 글 개수
	public int getBoardCount(Connection conn) throws SQLException {
		int cnt = 0;
		String sql = "select COUNT(*) from notice";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				cnt = rs.getInt(1);
			}
		} finally {
			close(rs, pstmt);
		}
		return cnt;
	}

	// 공지사항 목록
	public List<NoticeVO> getBoardPage(Connection conn, int start, int end) throws SQLException {
		List<NoticeVO> list = new ArrayList<NoticeVO>();
		NoticeVO vo = null;
		String sql = "select * from (select ROWNUM rnum, n.* from (select * from notice order by pin desc, notice_no desc) n)
		where rnum >= ? and rnum <= ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					vo = new NoticeVO();
					vo.setNotice_no(rs.getInt("notice_no"));
					vo.setId(rs.getString("id"));
					vo.setNotice_count(rs.getInt("notice_count"));
					vo.setNotice_title(rs.getString("notice_title"));
					vo.setNotice_contents(rs.getString("notice_contents"));
					vo.setNotice_write_date(rs.getDate("notive_write_date"));
					list.add(vo);
				} while (rs.next());
			}
		} finally {
			close(rs, pstmt);
		}
		return list;
	}
```
noticeList.jsp
```jsx
	<!--페이징 숫자-->
			<div class="pagediv">
				<c:if test="${startPage != endPage}">
					<c:forEach varStatus="s"  begin="${startPage}" end="${endPage}" step="1">
						<a href="noticeList.do?pageNum=${s.count}">${s.count}</a>	
					</c:forEach>
				</c:if>
			</div>
```
게시판 페이징을 위한 소스코드입니다. 글 목록은 DB의 pin이라는 컬럼을 0과 1로 제약조건을 걸어준 후, 체크가 된(=1)인 게시글을 먼저 정렬한 후, 날짜별로 정열하여 고정글이 최상단으로 올 수 있게 구현하였습니다. 
또한 고정글은 최대 5개까지만 설정할 수 있도록하여, 무분별한 고정글로 인해 가독성이 떨어지는 것을 방지하였습니다. 고정글 개수 카운트는 아래와 같이 작성하였습니다. 
noticeWrite.jsp

```jsx
<script type="text/javascript">
function goRegister(){
	var count = $('#count').val();
	
	if($('#count').val() < 5 || document.getElementById("pin").checked == false){
		var frm = document.write;
		frm.action = "<%=ctxPath%>/noticeWrite.do";
		frm.method = "post";
		frm.submit();		 
	}
	else {
		alert("고정글은 다섯개만 등록할수 있습니다.");
	}
}
</script>
```
```jsx
	<tr>
		<th>고정글</th>
			<td  id = 'fix' style="width: 14px;"><input type="checkbox" name="pin" id ="pin" value="1" ></td>
			<th style="float:right"> 
			<button type="button" id="btnRegister" onclick="goRegister()">등록</button>&nbsp;&nbsp; 
			<a href="<%=ctxPath%>/noticeList.do" style =" margin-right: 5px;">목록보기</a></th>
		</tr>
<input type = "hidden" id = "count" name = "count" value  = "${cnt}">
```
게시글을 등록한 후 submit전에 DB에 저장된 pin컬럼의 전체 합이 5가 넘으면 등록이되지 못하고 알림 창이 뜨도록 구현하였습니다. 
#### 게시글 검색

NoticeDAO.java
```jsx
	public List<NoticeVO> SearchNotice(Connection conn, String word, int startRnum, int endRnum) {
		List<NoticeVO> list = new ArrayList<NoticeVO>();
		String sql = "select * from(select rownum rnum, d.* from "
				+ "(select * from notice where notice_title like ? order by pin desc, notice_no desc) d) "
				+ "where rnum >= ? and rnum <= ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + word + "%");
			pstmt.setInt(2, startRnum);
			pstmt.setInt(3, endRnum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList<NoticeVO>();
				do {
					NoticeVO vo = new NoticeVO();
					vo.setNotice_no(rs.getInt("notice_no"));
					vo.setId(rs.getString("id"));
					vo.setNotice_count(rs.getInt("notice_count"));
					vo.setNotice_title(rs.getString("notice_title"));
					vo.setNotice_contents(rs.getString("notice_contents"));
					vo.setNotice_write_date(rs.getDate("Notive_write_date"));
					list.add(vo);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		return list;
	}
```
word 라는 String 변수의 값을 제목에서 찾아내어, 해당 게시글의 글번호와, 작성자, 조회수, 제목, 내용, 날짜를 셋해주는 소스코드입니다. 
List<NoticeVO>형의 메소드로 검색시 글 리스트가 보여지며 글 검색했을 시에도 페이징이 잘 되도록 startRnum과 endRnum변수를 주었습니다. 
  
#### 글 작성 (파일업로드)
공지사항 (notice)페이지의 글 작성은 관리자만 가능하므로 아래와 같이 관리자로그인을 한 경우(memberLev == 3)에만 글작성을 할 수 있도록 구현했습니다. 
noticeList.jsp
```jsx
		<tr>
			<th colspan="5">
		 <c:if test="${memberLev eq 3 }">	
			<a href="noticeFixCount.do">글작성</a>
			 </c:if>
			</th>
		</tr>
```

noticeWrite.java
```jsx
private void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = (String) request.getSession().getAttribute("sessionID");
		System.out.println(id);
		NoticeService nService = new NoticeService();
		String folderPath = getServletContext().getRealPath("/files");
		MultipartRequest mReq = new MultipartRequest(request, folderPath, 5 * 1024 * 1024, "utf-8",
				new DefaultFileRenamePolicy());
		List<String> saveFiles = new ArrayList<String>();
		List<String> originFiles = new ArrayList<String>();
		Enumeration<String> files = mReq.getFileNames();
		while (files.hasMoreElements()) {
			String name = files.nextElement();
			String filename = mReq.getFilesystemName(name);
			String originfilename = mReq.getOriginalFileName(name);
			saveFiles.add(filename);
			originFiles.add(originfilename);
		}	
		String title = mReq.getParameter("notice_title");
		String contents = mReq.getParameter("notice_contents");
		int pin = 0;
		if (mReq.getParameter("pin") != null){
		 pin = Integer.parseInt(mReq.getParameter("pin"));
		}
		NoticeVO vo = new NoticeVO();
		if (title != null && contents != null) {
			vo.setNotice_title(title);
			vo.setNotice_contents(contents);
			vo.setNotice_img(saveFiles);
			vo.setPin(pin);
			vo.setId(id);
			int result = nService.writeNotice(id, title, contents, saveFiles, pin);
			if (result < 0) {
				response.sendRedirect("<script>alert('오류가 발생했습니다.');</script>");
				return;
			}
		}
		response.sendRedirect("noticeList.do");
	}
```
NoticeDAO.java
```jsx
  public int writeNotice(Connection conn, String id, String title, String contents, List<String> img, int pin) {
		int result1 = 0;
		int result2 = 0;
		String query = "INSERT INTO notice (notice_no, id, notice_title, notice_contents,notice_count, pin)
		VALUES(notice_no_seq.nextval, ?, ?, ?, 0, ?)";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, title);
			pstmt.setString(3, contents);
			pstmt.setInt(4, pin);
			result1 = pstmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace();
		} finally {
			close(rs, pstmt);
		} 
		if (img == null) {
			result2 =1;
		}else {
		for (int i = 0; i < img.size(); i++) {
			query = "INSERT INTO tbl_img (img_no, notice_no, img) VALUES(img_seq.nextval, notice_no_seq.currval, ? )";
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, (String)img.get(i));
				result2 = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs, pstmt);
			}
		}
		}
		if (result1 != 0 || result2 != 0 ) 
			return 1;
			else return 0;
	}
```
```jsx
public int DeleteNotice(Connection conn, int no) {
		String sql = "DELETE FROM notice WHERE notice_no = ?";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		return result;
	}
```
글 작성시 이미지를 등록할 수 있습니다. 이미지를 등록하지 않았다면 notice테이블에 글만 인서트되며 이미지가 null이 아닐시에는 tbl_img에 해당 이미지를 인서트합니다. 
하나의 글 번호에 이미지가 여러장 입력될 시, 이미지의 사이즈만큼 쿼리문을 수행하며 이미지를 셋해줍니다.
이미지가 등록에 실패했다면 글 등록도 실패합니다. 


## 작동 영상
간단한 작동 영상입니다. 
[![예제](https://img.youtube.com/vi/hPoQP96emqs/0.jpg)](https://youtu.be/hPoQP96emqs) 
