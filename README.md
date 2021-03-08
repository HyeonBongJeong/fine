## 🌞 FINE 🌞
유기동물 입양 서비스 제공

## 목록 
1. [개요](#개요)
2. [기대효과](#기대효과)
3. [사용도구](#-사용도구)
4. [개발순서](#-개발순서)
5. [주요소스](#-주요소스)

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

#### 정현봉
- 유기견(입양, 찾기) 기능
- 파일 업,다운로드
- 공공 api사용하여 유기견의 목록 불러오기
- 유기견 입양 예약 기능
- 메인 페이지


##  🔧 개발순서
![Fine](https://user-images.githubusercontent.com/69295153/106555996-fa452280-6561-11eb-8402-275fefdcea22.jpg)

### DB 설계
![Copy of fine (1)](https://user-images.githubusercontent.com/69295153/106625198-cdbcf500-65b9-11eb-8d8d-5b2bf0f4b582.png)

### 테이블 정의서
![kh2조 semi_1](https://user-images.githubusercontent.com/69295153/106628659-6143f500-65bd-11eb-9410-fb32a3e1f782.jpg)
![kh2조 semi_2](https://user-images.githubusercontent.com/69295153/106628663-62752200-65bd-11eb-9b1d-0021e40a35b1.jpg)
![kh2조 semi_3](https://user-images.githubusercontent.com/69295153/106628665-630db880-65bd-11eb-9f7b-77147828e2a9.jpg)

### 유기견 찾기,  클래스 다이어그램
![sssddaa](https://user-images.githubusercontent.com/59170160/110359049-5bd83f80-8080-11eb-88cf-21c903e7d5d8.png)

### 주요소스 
#### 입양 게시판 페이징
![rList](https://user-images.githubusercontent.com/59170160/110350167-c97f6e00-8076-11eb-96f5-0408c5ab4229.png)
![rSearch](https://user-images.githubusercontent.com/59170160/110350170-ca180480-8076-11eb-985e-9e196bdc6283.png)
- Find_Adopt_List.java
```jsx
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String ctx = request.getContextPath();
	String id = (String) request.getSession().getAttribute("sessionID");
		System.out.println(id);
		if(request.getSession().getAttribute("sessionID") == null) {
			PrintWriter out = response.getWriter();
			out.append("<script>alert('로그인 후 이용해 주세요')</script>");
			out.println("<script>location.href='"+ctx+"/findHowMany.do'</script>"); 
		}else {
			FindService fService = new FindService();
			List<FindVO> list = new ArrayList<FindVO>();
			 
				int pageSize = 10; // 페이지당 읽어올 글수
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				int pageBlock = 10;
			

			int count = fService.getAdoptListCount();	
			System.out.println(count);
			String pageNum = request.getParameter("pageNum");
			if (pageNum == null) {
				pageNum = "1";
			}
			int currentPage = Integer.parseInt(pageNum);


			int pagecount = (count / pageSize) + (count % pageSize == 0 ? 0 : 1);
			int startPage = 1;
			int endPage = 1;
			if (currentPage % 10 == 0) {

				startPage = ((currentPage / 10) - 1) * pageBlock + 1;
			} else {
				startPage = ((currentPage / 10)) * pageBlock + 1;
			}

			endPage = startPage + pageBlock;
			if (endPage > pagecount) {
				endPage = pagecount;
			}
			 
			

			

			int startRnum = (currentPage - 1) * pageSize + 1;
			int endRnum = startRnum + pageSize - 1;
			
			
			
			request.setAttribute("currentPage", currentPage); 
			request.setAttribute("count", count);
			request.setAttribute("endPage", endPage);
			request.setAttribute("startPage", startPage);
			
			request.setAttribute("pagecount", pagecount);
			
			list = fService.getFindAdoptInfo(id, startRnum, endRnum);
			System.out.println("안녕히세여 :" + list);
			request.setAttribute("list", list);
			
			RequestDispatcher dis = request.getRequestDispatcher("./view/find/fine_find_Adopt_List.jsp");
			dis.forward(request, response);
		}
		
	
	}
```
- FindDAO.java
```jsx
	//입양 유기견 가져오기 메서드 DAO
	public List<FindVO> getFindAdoptInfo(int startRnum, int endRnum, String id, Connection conn, PreparedStatement pstmt,
			ResultSet rs) {
		List<FindVO> list = new ArrayList<FindVO>();
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		Date currentTime = new Date();
		String mTime = mSimpleDateFormat.format(currentTime);
		System.out.println(mTime);

		String sql = "select * from(select rownum rnum, d.* from (select * from dog where noticeEdt < " + mTime
				+ " and reservate = 0 and dog_kind_no = (select dog_kind_no from member where id= ?) order by desertionNo desc) d) where rnum >= ? and rnum <= ?";
		try {

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setInt(2, startRnum);
			pstmt.setInt(3, endRnum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					FindVO vo = new FindVO();
					vo.setDesertionNo(rs.getString("desertionNo"));
					vo.setHappenPlace(rs.getString("happenplace"));
					vo.setAge(rs.getString("age"));
					vo.setCareAddr(rs.getString("care_adress"));
					vo.setCareNm(rs.getString("care_name"));
					vo.setCareTel(rs.getString("care_tel"));
					vo.setColorCd(rs.getString("colorcd"));
					vo.setFilename(rs.getString("filename"));
					vo.setHappenDt(rs.getString("happendt"));
					vo.setKindCd(rs.getString("DOG_KIND_NO"));
					vo.setNeuterYn(rs.getString("neuteryn"));
					vo.setNoticeEdt(rs.getString("noticeEdt"));
					vo.setNoticeSdt(rs.getString("noticeSdt"));
					vo.setOfficetel(rs.getString("officetel"));
					vo.setOrgNm(rs.getString("orgnm"));
					vo.setPopfile(rs.getString("popfile"));
					vo.setProcessState(rs.getString("processstate"));
					vo.setSexCd(rs.getString("sexcd"));
					vo.setSpecialMark(rs.getString("specialMark"));
					vo.setWeight(rs.getString("weight"));
					list.add(vo);
				} while (rs.next());

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}
	//입양 유기견 게시판 페이징을 위한 메서드
	public int getAdoptListCount(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		Date currentTime = new Date();
		String mTime = mSimpleDateFormat.format(currentTime);
		System.out.println(mTime);
		int result = 0;
		String sql = "select count(*) from dog where noticeEdt < " + mTime;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					result = rs.getInt(1);
				} while (rs.next());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
```
- 입양할 유기견을 보여주는 메서드 입니다.
- 무분별한 입양을 막기 위해 세션을 체크하여 로그인 정보가 없다면 접근하지 못하게 코딩하였습니다.
- 그리고 처음 불러오는 리스트는 처음 회원가입시에 선호하는 견종을 고르게 되는데 아무런 검색정보가 없다면 자신이 선호하는 견종위주로 가져오게 코딩하였습니다.

#### 입양 게시판 페이징(검색)
- Find_Adopt_Search.java
```jsx
//입양할 유기견 검색리스트
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String ctx = request.getContextPath();
		if(request.getSession().getAttribute("sessionID") == null) {
			PrintWriter out = response.getWriter();
			out.append("<script>alert('로그인 후 이용해 주세요')</script>");
			out.println("<script>location.href='"+ctx+"/findHowMany.do'</script>"); 
		}else {
			String sido = request.getParameter("sido");
			String sigungu = request.getParameter("sigungu");
			String address = sido+ " "+ sigungu;
			String dogKind = request.getParameter("dogKind");
			System.out.println("서블릿 : "+dogKind);
			if(dogKind == null) {
				System.out.println("견종못불러옴");
			}else {
				
				System.out.println("견종" + dogKind);
			}
			
			
			FindService fService = new FindService();
			List<FindVO> list = new ArrayList<FindVO>();
			 
				int pageSize = 10; // 페이지당 읽어올 글수
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				int pageBlock = 10;
			

			int count = fService.getAdoptSearchBoardCount(address,dogKind);	
			System.out.println("불러온 개수: " + count);
			String pageNum = request.getParameter("pageNum");
			if (pageNum == null) {
				pageNum = "1";
			}
			int currentPage = Integer.parseInt(pageNum);


			int pagecount = (count / pageSize) + (count % pageSize == 0 ? 0 : 1);
			int startPage = 1;
			int endPage = 1;
			if (currentPage % 10 == 0) {

				startPage = ((currentPage / 10) - 1) * pageBlock + 1;
			} else {
				startPage = ((currentPage / 10)) * pageBlock + 1;
			}

			endPage = startPage + pageBlock;
			if (endPage > pagecount) {
				endPage = pagecount;
			}
			 
			

			

			int startRnum = (currentPage - 1) * pageSize + 1;
			int endRnum = startRnum + pageSize - 1;
			
			
			
			request.setAttribute("sido", sido);
			request.setAttribute("sigungu", sigungu);
			request.setAttribute("dogKind", dogKind);
			request.setAttribute("currentPage", currentPage); 
			request.setAttribute("count", count);
			request.setAttribute("endPage", endPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("pagecount", pagecount);
			list = fService.getAdoptSearchDogPage(startRnum, endRnum, address, dogKind);
			request.setAttribute("list", list);
			System.out.println(list);
			RequestDispatcher dis = request.getRequestDispatcher("/view/find/fine_find_Adopt_search.jsp");
			dis.forward(request, response);
		}
	
	}
```
- fine_find_Adopt_search.jsp kakaoMap api script
```jsx
var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
mapOption = { 
    center: new kakao.maps.LatLng(36.991879423239666, 128.03755800986698), // 지도의 중심좌표
    level: 13 // 지도의 확대 레벨
};

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
//확대 축소
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
var geocoder = new kakao.maps.services.Geocoder();
//마커를 표시할 위치와 title 객체 배열입니다 
var positions = document.getElementsByClassName('address');
var careName = document.getElementsByClassName('careName');
	for(var i=0; i<=positions.length; i++){
	console.log(positions[i]);	
		
	}
	
	for (var i=0; i < positions.length ; i++) {
		geocoder.addressSearch(positions[i].innerText, function(result, status) {

		    // 정상적으로 검색이 완료됐으면 
		     if (status === kakao.maps.services.Status.OK) {

		        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

		        // 결과값으로 받은 위치를 마커로 표시합니다
		        var marker = new kakao.maps.Marker({
		            map: map,
		            position: coords
		        });

		        // 인포윈도우로 장소에 대한 설명을 표시합니다
		       
		        var infowindow = new kakao.maps.InfoWindow({
		            content:  '<div style="width:150px;text-align:center;padding:6px 0;"><a href="https://map.kakao.com/link/to/'+careName[i]+',' +coords.Ma+','+ coords.La+'" style="color:blue" target="_blank">길찾기</a></div>'
		        });
		        kakao.maps.event.addListener(marker, 'click', function() {
		            // 마커 위에 인포윈도우를 표시합니다
		            infowindow.open(map, marker);  
		      });
		       
				

		        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
		    } 
		})


		}; 
```		
- FindDAO.java
```jsx
//입양 할 유기견 검색 메서드
	public int getAdoptSearchBoardCount(String address, String dogKind, Connection conn, PreparedStatement pstmt,
			ResultSet rs) {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		Date currentTime = new Date();
		String mTime = mSimpleDateFormat.format(currentTime);
		System.out.println(mTime);
		int cnt = 0;
		String sql = "select count(*) from dog where care_adress like ?  and reservate = 0 and dog_kind_no in (select dog_kind_no from dog_kind where kind like ?) and noticeEdt <"
				+ mTime;

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + address + "%");
			pstmt.setString(2, "%" + dogKind + "%");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					cnt = rs.getInt(1);
					System.out.println(cnt);
				} while (rs.next());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("검색개수!!!!" + cnt);
		return cnt;
	}
```
- 위의 리스트 불러오는 것과 마찬가지로 검색어를 가지고 DB에서 값을 불러옵니다.
- 리스트에서 불러온 유기견의 시/도 정보를 카카오 api에도 뿌려줍니다.
#### 입양 유기견 상세정보
![rDetail](https://user-images.githubusercontent.com/59170160/110350194-cdab8b80-8076-11eb-81c7-bac0ac61e7fe.png)
![rDetailMap](https://user-images.githubusercontent.com/59170160/110350165-c8e6d780-8076-11eb-9fbf-12fdf3cfa8ea.png)
- Find_Adopt_Detail.java

```jsx
//입양할 유기견 상세 정보
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String ctx = request.getContextPath();
		String id = (String) request.getSession().getAttribute("sessionID");
		System.out.println(id);
		if(request.getSession().getAttribute("sessionID") == null) {
			PrintWriter out = response.getWriter();
			out.append("<script>alert('로그인 후 이용해 주세요')</script>");
			out.println("<script>location.href='"+ctx+"/findHowMany.do'</script>"); 
		}else {
			FindService fService = new FindService(); 
			
			int no = Integer.parseInt(request.getParameter("no"));
			List<FindVO> list = new ArrayList<FindVO>();
			list = fService.getFindDetail(no); 
			System.out.println(list);
			request.setAttribute("list", list);
			//response.sendRedirect("../training/fine_training_Dtail.jsp?trn_no="+no);
			RequestDispatcher disp = request.getRequestDispatcher("/view/find/fine_find_Adopt_Detail.jsp");
			disp.forward(request, response);	
		}
		
	}
```
- FindDAO.java
```jsx
//입양할 유기견 상세 정보
	public List<FindVO> getFindInfo(int no, Connection conn, PreparedStatement pstmt, ResultSet rs) {
		List<FindVO> list = new ArrayList<FindVO>();
		String sql = "select dog.*, dog_kind.kind from dog, dog_kind where dog.dog_kind_no = dog_kind.dog_kind_no and  desertionno=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					FindVO vo = new FindVO();
					vo.setDesertionNo(rs.getString("desertionNo"));
					vo.setHappenPlace(rs.getString("happenplace"));
					vo.setAge(rs.getString("age"));
					vo.setCareAddr(rs.getString("care_adress"));
					vo.setCareNm(rs.getString("care_name"));
					vo.setCareTel(rs.getString("care_tel"));
					vo.setColorCd(rs.getString("colorcd"));
					vo.setFilename(rs.getString("filename"));
					vo.setHappenDt(rs.getString("happendt"));
					vo.setKindCd(rs.getString("kind"));
					vo.setNeuterYn(rs.getString("neuteryn"));
					vo.setNoticeEdt(rs.getString("noticeEdt"));
					vo.setNoticeSdt(rs.getString("noticeSdt"));
					vo.setOfficetel(rs.getString("officetel"));
					vo.setOrgNm(rs.getString("orgnm"));
					vo.setPopfile(rs.getString("popfile"));
					vo.setProcessState(rs.getString("processstate"));
					vo.setSexCd(rs.getString("sexcd"));
					vo.setSpecialMark(rs.getString("specialMark"));
					vo.setWeight(rs.getString("weight"));
					list.add(vo);
					System.out.println(list);
				} while (rs.next());
			}
		} catch (SQLException e) {
			System.out.println("실팽");
			e.printStackTrace();
		}

		return list;
	}
```
- 유기된 유기견의 리스트 중 글번호를 가지고 유기견의 상세정보를 가져와 화면에 뿌려주는 데 이때 리스트에서 처럼 카카오 api를 사용하여 위치정보도 나타내어 줍니다.
#### 입양 페이지 이동할 때 회원 등급 체크
- Find_Reservation.java
```jsx
//입양예약 페이지 이동 메서드
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		List<FindVO> list = new ArrayList<FindVO>();
		List<MemberVO> lists = new ArrayList<MemberVO>();
		String ctx = request.getContextPath();
		
		if (request.getSession().getAttribute("sessionID") == null) {
			PrintWriter out = response.getWriter();
			out.append("<script>alert('로그인 후 이용해 주세요')</script>");
			out.println("<script>location.href='"+ctx+"/findHowMany.do'</script>");
		} else {
			FindService fService = new FindService();
			String id = (String) request.getSession().getAttribute("sessionID");
		
	
			int grade = fService.gerMemberGrade(id);

			if (grade >= 3) {
				lists = fService.getMembername(id);
			} else if (grade < 3) {

				PrintWriter out = response.getWriter();
				out.append("<script>alert('입양하기위한 등급이 낮습니다 훈련정보를 보고 퀴즈를 풀어주세요!')</script>");
				out.println("<script>history.back()</script>");
				out.flush();
				out.close();
			}
			int no = Integer.parseInt(request.getParameter("dogNum"));
			list = fService.getFindDetail(no);
			System.out.println(list);
			if (list == null) {
				System.out.println("예약서블릿의 리스트가 비어있습니다");
			} else
			request.setAttribute("lists", lists);
			request.setAttribute("list", list);
			RequestDispatcher disp = request.getRequestDispatcher("/view/find/find_reservation.jsp");
			disp.forward(request, response);

		}

	}
```
- FindDAO.java
```jsx
//회원의 등급을 가져오는 메서드
	public int gerMemberGrade(String id, Connection conn, PreparedStatement pstmt, ResultSet rs) {
		String sql = "select grade from member where id = ?";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = Integer.parseInt(rs.getString("grade"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	//입양하기 위한 회원의 정보를 가져오는 메서드
	public List<MemberVO> getMemberName(Connection conn, DataSource ds, ResultSet rs, PreparedStatement pstmt,
			String id) {
		// TODO Auto-generated method stub
		List<MemberVO> name = new ArrayList<MemberVO>();
		String sql = "select id, name, address, phone, birthday from member where id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					MemberVO vo = new MemberVO();
					vo.setId(rs.getString("id"));
					vo.setAddress(rs.getString("address"));
					vo.setPhone(rs.getString("phone"));
					vo.setName(rs.getString("name"));
					name.add(vo);
				} while (rs.next());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return name;
	}
	//입양할 유기견 상세 정보
	public List<FindVO> getFindInfo(int no, Connection conn, PreparedStatement pstmt, ResultSet rs) {
		List<FindVO> list = new ArrayList<FindVO>();
		String sql = "select dog.*, dog_kind.kind from dog, dog_kind where dog.dog_kind_no = dog_kind.dog_kind_no and  desertionno=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					FindVO vo = new FindVO();
					vo.setDesertionNo(rs.getString("desertionNo"));
					vo.setHappenPlace(rs.getString("happenplace"));
					vo.setAge(rs.getString("age"));
					vo.setCareAddr(rs.getString("care_adress"));
					vo.setCareNm(rs.getString("care_name"));
					vo.setCareTel(rs.getString("care_tel"));
					vo.setColorCd(rs.getString("colorcd"));
					vo.setFilename(rs.getString("filename"));
					vo.setHappenDt(rs.getString("happendt"));
					vo.setKindCd(rs.getString("kind"));
					vo.setNeuterYn(rs.getString("neuteryn"));
					vo.setNoticeEdt(rs.getString("noticeEdt"));
					vo.setNoticeSdt(rs.getString("noticeSdt"));
					vo.setOfficetel(rs.getString("officetel"));
					vo.setOrgNm(rs.getString("orgnm"));
					vo.setPopfile(rs.getString("popfile"));
					vo.setProcessState(rs.getString("processstate"));
					vo.setSexCd(rs.getString("sexcd"));
					vo.setSpecialMark(rs.getString("specialMark"));
					vo.setWeight(rs.getString("weight"));
					list.add(vo);
					System.out.println(list);
				} while (rs.next());
			}
		} catch (SQLException e) {
			System.out.println("실팽");
			e.printStackTrace();
		}

		return list;
	}
```
- 입양예약 페이지로 이동하기 전에 회원의 등급을 먼저 체크 한 후에 기준 미달이라면 퀴즈 화면으로 넘어가게 코딩하였습니다.
- 기준에 준하면 회원의 정보와 유기견의 정보를 가지고 예약 페이지로 이동하게 구현하였습니다.
#### 입양 정보
![mResernavtion](https://user-images.githubusercontent.com/59170160/110350192-cdab8b80-8076-11eb-87f3-29ec055aad01.png)
- Find_Reservation_Adopt.java
```jsx
    //입양예약 메서드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String ctx = request.getContextPath();
		String id = (String) request.getSession().getAttribute("sessionID");
		if(request.getSession().getAttribute("sessionID") == null) {
				PrintWriter out = response.getWriter();
				out.append("<script>alert('로그인 후 이용해 주세요')</script>");
				out.println("<script>location.href='"+ctx+"/findHowMany.do'</script>"); 
				out.flush();
				out.close();
			
		}else {
				FindService fService = new FindService();
				int grade = fService.gerMemberGrade(id);
				if (grade >= 3) {
					int dogNum = Integer.parseInt(request.getParameter("dogNum"));
					String careNm = request.getParameter("careName"); //보호소 번호 가져와야함
					String careNoResult = fService.getCareNo(careNm); 
					String adoptDate = request.getParameter("date");
					SimpleDateFormat format1 = new SimpleDateFormat ("yyyyMMdd");
					Calendar time = Calendar.getInstance();					       
					String today = format1.format(time.getTime());
					
				
					if(adoptDate.equals("")) {
						PrintWriter out = response.getWriter();
						out.append("<script>alert('날짜를 선택해 주세요')</script>");
						out.println("<script>history.back()</script>");
						out.flush();
						out.close();
					}else {
						if(Integer.parseInt(adoptDate) < Integer.parseInt(today)) {
							PrintWriter out = response.getWriter();
							out.append("<script>alert('오늘날짜 이후를 선택해주세요')</script>");
							out.println("<script>history.back()</script>");
							out.flush();
							out.close();
						}
					}
					String[] chk = request.getParameterValues("agree");
					if(chk == null) {
						PrintWriter out = response.getWriter();
						out.append("<script>alert('약관에 동의해 주세요')</script>");
						out.println("<script>history.back()</script>");
						out.flush();
						out.close();
					}else {
						if(chk.length != 2) {
							PrintWriter out = response.getWriter();
							out.append("<script>alert('약관에 동의해 주세요')</script>");
							out.println("<script>history.back()</script>");
						}else if(chk.length == 2){
							int result = fService.insertReservatoin(careNoResult,adoptDate,dogNum,id);
							if(result == 1) {
								int update = fService.updateDog(dogNum);
								if(update == 1) {
									response.sendRedirect("./view/find/find_reservation_done.jsp");									
								}else {
									PrintWriter out = response.getWriter();
									out.append("<script>alert('이미 누가 예약을 끝냈습니다. 다른 유기견도 기다리고 있습니다!')</script>");
									out.println("<script>'"+ctx+"/findHowMany.do'</script>");
									out.flush();
									out.close();
								}
								
							}else {
								PrintWriter out = response.getWriter();
								out.append("<script>alert('죄송합니다 입양예약에 실패하였습니다! 다시 시도해 주세요')</script>");
								out.println("<script>'"+ctx+"/findHowMany.do'</script>");
								out.flush();
								out.close();
							}
							
						}	
					}
					
					//db저장
				}else {
					PrintWriter out = response.getWriter();
					out.append("<script>alert('부적절한 등급입니다.')</script>");
					out.println("<script>location.href='./view/main/index.jsp'</script>");
					out.flush();
					out.close();
				}
		}
	
	}
```
- FindDAO.java
```jsx
//입양예약 메서드
	public int insertReservatoin(String careNoResult, String adoptDate, int dogNum, String id, Connection conn,
			PreparedStatement pstmt, DataSource ds) {
		int result = 0;
		String sql = "insert into reservation(RESERCATION_NO,DESERTIONNO,ID,CARE_NO,RESERVATION_DATE) "
				+ "values(RESERVATION_SEQ.NEXTVAL,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dogNum);
			pstmt.setString(2, id);
			pstmt.setString(3, careNoResult);
			pstmt.setString(4, adoptDate);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
```
- 회원의 등급이 기준에 준하면 입양 예약을 할 수 있는데 예약 form에서 넘어온 정보를 가져옵니다.
- 날짜를 JdatePicker로 가져와 비어있는 지, 예약한 사람이 있는지 Calender 메서드를 활용하여 날짜 비교 후 모든 검증이 통과되면 예약을 할 수 있도록 구현하였습니다. 
- 
#### 입양 관련 서류 다운로드
![rReservationOk](https://user-images.githubusercontent.com/59170160/110350169-ca180480-8076-11eb-8c77-8cd9c20d052d.png)
- Find_document_down.java
```jsx
    //입양 관련서류 파일 다운로드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//게시글 첨부파일 다운 처리용 컨트롤러
		
		request.setCharacterEncoding("utf-8");
		//프로젝트 내에 파일이 저장된 폴더의 경로정보 얻어옴
		String readFolder = request.getSession().getServletContext().getRealPath("/upload/adoptfile");
		String oolean = request.getParameter("file");
		System.out.println(oolean);
		ServletOutputStream downOut =
				response.getOutputStream();
				File downFile = new File(readFolder + "/" + oolean);
				response.setContentType("text/plain; charset=utf-8");
				String originalFileName = "findDoc.hwp";
				
				
				//한글 파일명 인코딩 처리함
				response.addHeader("Content-Disposition",
				"attachment; filename=\"" +
				new String(originalFileName .getBytes("UTF-8"),"ISO-8859-1") + "\"");
				response.setContentLength((int)downFile.length());
				BufferedInputStream bin = new BufferedInputStream(
				new FileInputStream(downFile));
				int read = -1;
				while((read = bin.read()) != -1){
				downOut.write(read);
				downOut.flush();
				}
				downOut.close();
				bin.close();
	}
```
- 입양예약이 완료 된 후 필요 서류를 지참해야 함으로 서류 다운로드 기능을 구현하였습니다.
- ServletOutputStream 으로 다운받을 파일 명을 입력하고 인코딩 처리 후 다운이 되게끔 구현하였습니다.
### 잃어버린 유기견 리스트 
![findList](https://user-images.githubusercontent.com/59170160/110350175-cab09b00-8076-11eb-8fd4-dc33986fc48d.png)
![findSearch](https://user-images.githubusercontent.com/59170160/110350178-cb493180-8076-11eb-9872-70d9b21e3992.png)
- Find_Lists.java
```jsx
 //잃어버린 유기견 리스트
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		FindService fService = new FindService();
		List<FindVO> list = new ArrayList<FindVO>();
		 
			int pageSize = 10; // 페이지당 읽어올 글수
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			int pageBlock = 10;
		

		int count = fService.getBoardCount(); 	
		System.out.println(count);
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);


		int pagecount = (count / pageSize) + (count % pageSize == 0 ? 0 : 1);
		int startPage = 1;
		int endPage = 1;
		if (currentPage % 10 == 0) {

			startPage = ((currentPage / 10) - 1) * pageBlock + 1;
		} else {
			startPage = ((currentPage / 10)) * pageBlock + 1;
		}

		endPage = startPage + pageBlock;
		if (endPage > pagecount) {
			endPage = pagecount;
		}
		 
		

		

		int startRnum = (currentPage - 1) * pageSize + 1;
		int endRnum = startRnum + pageSize - 1;
		
		
		
		request.setAttribute("currentPage", currentPage); 
		request.setAttribute("count", count);
		request.setAttribute("endPage", endPage);
		request.setAttribute("startPage", startPage);
		
		request.setAttribute("pagecount", pagecount);
		
		list = fService.getFindInfo(startRnum, endRnum);
		System.out.println("안녕히세여 :" + list);
		request.setAttribute("list", list);
		
		RequestDispatcher dis = request.getRequestDispatcher("./view/find/fine_find_List.jsp");
		dis.forward(request, response);
	
	}

```
- 유기견 입양 기능과 동일하지만 찾는 기능은 입양이 아니므로 비회원도 찾을 수 있도록 구현하였습니다.
- FindDAO.java
```jsx
//페이징을 위한 데이터 개수 세는 메서드
	public int getBoardCount(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		int result = 0;
		String sql = "select count(*) from dog";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					result = rs.getInt(1);
				} while (rs.next());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	//유기견 찾기 정보 불러오기 메서드
	public List<FindVO> getFindInfo(int startRnum, int endRnum, Connection conn, PreparedStatement pstmt,
			ResultSet rs) {
		List<FindVO> list = new ArrayList<FindVO>();
		String sql = "select * from(select rownum rnum, d.* from (select * from dog order by desertionNo desc) d) where rnum >= ? and rnum <= ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRnum);
			pstmt.setInt(2, endRnum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					FindVO vo = new FindVO();
					vo.setDesertionNo(rs.getString("desertionNo"));
					vo.setHappenPlace(rs.getString("happenplace"));
					vo.setAge(rs.getString("age"));
					vo.setCareAddr(rs.getString("care_adress"));
					vo.setCareNm(rs.getString("care_name"));
					vo.setCareTel(rs.getString("care_tel"));
					vo.setColorCd(rs.getString("colorcd"));
					vo.setFilename(rs.getString("filename"));
					vo.setHappenDt(rs.getString("happendt"));
					vo.setKindCd(rs.getString("DOG_KIND_NO"));
					vo.setNeuterYn(rs.getString("neuteryn"));
					vo.setNoticeEdt(rs.getString("noticeEdt"));
					vo.setNoticeSdt(rs.getString("noticeSdt"));
					vo.setOfficetel(rs.getString("officetel"));
					vo.setOrgNm(rs.getString("orgnm"));
					vo.setPopfile(rs.getString("popfile"));
					vo.setProcessState(rs.getString("processstate"));
					vo.setSexCd(rs.getString("sexcd"));
					vo.setSpecialMark(rs.getString("specialMark"));
					vo.setWeight(rs.getString("weight"));
					list.add(vo);

				} while (rs.next());
				return list;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}
```
- 유기견 입양 기능과 동일
### 잃어버린 유기견 리스트(검색) 
- Find_Search.java
```jsx
    //잃어버린 유기견 검색 메서드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String sido = request.getParameter("sido");
		String sigungu = request.getParameter("sigungu");
		String address = sido+ " "+ sigungu;
		String dogKind = request.getParameter("dogKind");
		String happenDt = request.getParameter("happenDt");
		System.out.println("서블릿 : " + happenDt);
		System.out.println("서블릿 : "+ address);
		System.out.println("서블릿 : "+dogKind);
		if(dogKind == null) {
			System.out.println("견종못불러옴");
		}else {
			
			System.out.println("견종" + dogKind);
		}
		
		
		FindService fService = new FindService();
		List<FindVO> list = new ArrayList<FindVO>();
		 
			int pageSize = 10; // 페이지당 읽어올 글수
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			int pageBlock = 10;
		

		int count = fService.getSearchBoardCount(address,dogKind, happenDt);	
		System.out.println("불러온 개수: " + count);
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);


		int pagecount = (count / pageSize) + (count % pageSize == 0 ? 0 : 1);
		int startPage = 1;
		int endPage = 1;
		if (currentPage % 10 == 0) {

			startPage = ((currentPage / 10) - 1) * pageBlock + 1;
		} else {
			startPage = ((currentPage / 10)) * pageBlock + 1;
		}

		endPage = startPage + pageBlock;
		if (endPage > pagecount) {
			endPage = pagecount;
		}
		 
		

		

		int startRnum = (currentPage - 1) * pageSize + 1;
		int endRnum = startRnum + pageSize - 1;
		
		
		
		request.setAttribute("sido", sido);
		request.setAttribute("sigungu", sigungu);
		request.setAttribute("dogKind", dogKind);
		request.setAttribute("happenDt", happenDt);
		request.setAttribute("currentPage", currentPage); 
		request.setAttribute("count", count);
		request.setAttribute("endPage", endPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("pagecount", pagecount);
		list = fService.getSearchDogPage(startRnum, endRnum, address, dogKind, happenDt);
		request.setAttribute("list", list);
		System.out.println(list);
		RequestDispatcher dis = request.getRequestDispatcher("/view/find/fine_find_search.jsp");
		dis.forward(request, response);
	}
```

- FindDAO.java
```jsx
//유기견 찾기 검색 리스트 갯수 세는 메서드
public int getSearchBoardCount(String address, String dogKind, String happenDt, Connection conn,
			PreparedStatement pstmt, ResultSet rs) {
		int cnt = 0;
		String sql = "select count(*) from dog where care_adress like ?  and dog_kind_no in (select dog_kind_no from dog_kind where kind like ?) and happenDt > ?";

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + address + "%");
			pstmt.setString(2, "%" + dogKind + "%");
			pstmt.setString(3, happenDt);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					cnt = rs.getInt(1);
					System.out.println(cnt);
				} while (rs.next());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("검색개수!!!!" + cnt);
		return cnt;

	}
	//검색한 유기견 찾기 정보 메서드
	public List<FindVO> getSearchDogPage(String address, String dogKind, String happenDt, int startRnum, int endRnum,
			Connection conn, PreparedStatement pstmt, ResultSet rs) {
		List<FindVO> list = new ArrayList<FindVO>();

		String sql = "select * from(select rownum rnum, d.* from "
				+ "(select * from dog where dog_kind_no = (select dog_kind_no from dog_kind where kind like ?) and care_adress like ? and happenDt > ?) d) "
				+ "where rnum >= ? and rnum <= ?";
		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + dogKind + "%");
			pstmt.setString(2, "%" + address + "%");
			pstmt.setString(3, happenDt);
			pstmt.setInt(4, startRnum);
			pstmt.setInt(5, endRnum);
			rs = pstmt.executeQuery();
			System.out.println("ㅎㅇㅎㅇ");
			if (rs.next()) {
				do {
					FindVO vo = new FindVO();
					vo.setDesertionNo(rs.getString("desertionNo"));
					vo.setHappenPlace(rs.getString("happenplace"));
					vo.setAge(rs.getString("age"));
					vo.setCareAddr(rs.getString("care_adress"));
					vo.setCareNm(rs.getString("care_name"));
					vo.setCareTel(rs.getString("care_tel"));
					vo.setColorCd(rs.getString("colorcd"));
					vo.setFilename(rs.getString("filename"));
					vo.setHappenDt(rs.getString("happendt"));
					vo.setKindCd(rs.getString("DOG_KIND_NO"));
					vo.setNeuterYn(rs.getString("neuteryn"));
					vo.setNoticeEdt(rs.getString("noticeEdt"));
					vo.setNoticeSdt(rs.getString("noticeSdt"));
					vo.setOfficetel(rs.getString("officetel"));
					vo.setOrgNm(rs.getString("orgnm"));
					vo.setPopfile(rs.getString("popfile"));
					vo.setProcessState(rs.getString("processstate"));
					vo.setSexCd(rs.getString("sexcd"));
					vo.setSpecialMark(rs.getString("specialMark"));
					vo.setWeight(rs.getString("weight"));
					list.add(vo);
				} while (rs.next());

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}
```
- 유기견 입양 기능과 동일
### 잃어버린 유기견 상세 정보 
![findDetail](https://user-images.githubusercontent.com/59170160/110350174-cab09b00-8076-11eb-8a8b-47ebd9e94c2e.png)
![fineDetailMap](https://user-images.githubusercontent.com/59170160/110350180-cb493180-8076-11eb-8c5f-ef2e22027f06.png)

- Find_Detail.java
```jsx
 //유기견 찾기 상세정보
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		int no = Integer.parseInt(request.getParameter("no"));
		FindService fService = new FindService(); 
		List<FindVO> list = new ArrayList<FindVO>();
		list = fService.getFindDetail(no); 
		System.out.println(list);
		request.setAttribute("list", list);
		//response.sendRedirect("../training/fine_training_Dtail.jsp?trn_no="+no);
		RequestDispatcher disp = request.getRequestDispatcher("/view/find/fine_find_Detail.jsp");
		disp.forward(request, response);
	}
```
- FindDAO.java
```jsx
//입양할 유기견 상세 정보
	public List<FindVO> getFindInfo(int no, Connection conn, PreparedStatement pstmt, ResultSet rs) {
		List<FindVO> list = new ArrayList<FindVO>();
		String sql = "select dog.*, dog_kind.kind from dog, dog_kind where dog.dog_kind_no = dog_kind.dog_kind_no and  desertionno=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					FindVO vo = new FindVO();
					vo.setDesertionNo(rs.getString("desertionNo"));
					vo.setHappenPlace(rs.getString("happenplace"));
					vo.setAge(rs.getString("age"));
					vo.setCareAddr(rs.getString("care_adress"));
					vo.setCareNm(rs.getString("care_name"));
					vo.setCareTel(rs.getString("care_tel"));
					vo.setColorCd(rs.getString("colorcd"));
					vo.setFilename(rs.getString("filename"));
					vo.setHappenDt(rs.getString("happendt"));
					vo.setKindCd(rs.getString("kind"));
					vo.setNeuterYn(rs.getString("neuteryn"));
					vo.setNoticeEdt(rs.getString("noticeEdt"));
					vo.setNoticeSdt(rs.getString("noticeSdt"));
					vo.setOfficetel(rs.getString("officetel"));
					vo.setOrgNm(rs.getString("orgnm"));
					vo.setPopfile(rs.getString("popfile"));
					vo.setProcessState(rs.getString("processstate"));
					vo.setSexCd(rs.getString("sexcd"));
					vo.setSpecialMark(rs.getString("specialMark"));
					vo.setWeight(rs.getString("weight"));
					list.add(vo);
					System.out.println(list);
				} while (rs.next());
			}
		} catch (SQLException e) {
			System.out.println("실팽");
			e.printStackTrace();
		}

		return list;
	}
```
- 유기견 입양 기능과 동일
### 제휴 보호소 관리자 별 보호중인 유기견 목록 

![mnageLsit](https://user-images.githubusercontent.com/59170160/110350188-cd12f500-8076-11eb-8d97-f15aefcf462d.png)
- Fine_find_manage_List.java
```jsx
    //매니저 자신의 보호소에 등록된 유기견 정보 가져오기 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	
		
				String checkLev = (String) request.getSession().getAttribute("memberLev");
				String checkId = (String) request.getSession().getAttribute("sessionID");
				
					FindService fService = new FindService();
					List<FindVO> list = new ArrayList<FindVO>();
					 
						int pageSize = 10; // 페이지당 읽어올 글수
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						int pageBlock = 10;
					

					int count = fService.getManageAdoptBoardCount(checkId, checkLev); 	
					System.out.println(count);
					String pageNum = request.getParameter("pageNum");
					if (pageNum == null) {
						pageNum = "1";
					}
					int currentPage = Integer.parseInt(pageNum);


					int pagecount = (count / pageSize) + (count % pageSize == 0 ? 0 : 1);
					int startPage = 1;
					int endPage = 1;
					if (currentPage % 10 == 0) {

						startPage = ((currentPage / 10) - 1) * pageBlock + 1;
					} else {
						startPage = ((currentPage / 10)) * pageBlock + 1;
					}

					endPage = startPage + pageBlock;
					if (endPage > pagecount) {
						endPage = pagecount;
					}
					 
					

					

					int startRnum = (currentPage - 1) * pageSize + 1;
					int endRnum = startRnum + pageSize - 1;
					
					
					
					request.setAttribute("currentPage", currentPage); 
					request.setAttribute("count", count);
					request.setAttribute("endPage", endPage);
					request.setAttribute("startPage", startPage);
					
					request.setAttribute("pagecount", pagecount);
					
					list = fService.getFindManageList(checkId, checkLev, startRnum, endRnum);
					System.out.println("안녕히세여 :" + list);
					request.setAttribute("list", list);
					
					RequestDispatcher dis = request.getRequestDispatcher("./view/find/fine_find_Adopt_manage_List.jsp");
					dis.forward(request, response);
				}
```
- FindDAO.java
```jsx
//보호소 별 보호중인 유기견 목록가져오는 메서드
	public List<FindVO> getFindManageList(String checkId, String lev, int startRnum, int endRnum, Connection conn,
			PreparedStatement pstmt, ResultSet rs) {
		List<FindVO> list = new ArrayList<FindVO>();
		String sql = "select * from(select rownum rnum, d.* from (select * from dog where care_name = (select care_name from care where care_no =(select care_no from member where id = ? and lev = ?))order by desertionNo desc) d) where rnum >= ? and rnum <= ?";
		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, checkId);
			pstmt.setString(2, lev);
			pstmt.setInt(3, startRnum);
			pstmt.setInt(4, endRnum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					FindVO vo = new FindVO();
					vo.setDesertionNo(rs.getString("desertionNo"));
					vo.setHappenPlace(rs.getString("happenplace"));
					vo.setAge(rs.getString("age"));
					vo.setCareAddr(rs.getString("care_adress"));
					vo.setCareNm(rs.getString("care_name"));
					vo.setCareTel(rs.getString("care_tel"));
					vo.setColorCd(rs.getString("colorcd"));
					vo.setFilename(rs.getString("filename"));
					vo.setHappenDt(rs.getString("happendt"));
					vo.setKindCd(rs.getString("DOG_KIND_NO"));
					vo.setNeuterYn(rs.getString("neuteryn"));
					vo.setNoticeEdt(rs.getString("noticeEdt"));
					vo.setNoticeSdt(rs.getString("noticeSdt"));
					vo.setOfficetel(rs.getString("officetel"));
					vo.setOrgNm(rs.getString("orgnm"));
					vo.setPopfile(rs.getString("popfile"));
					vo.setProcessState(rs.getString("processstate"));
					vo.setSexCd(rs.getString("sexcd"));
					vo.setSpecialMark(rs.getString("specialMark"));
					vo.setWeight(rs.getString("weight"));
					list.add(vo);
				} while (rs.next());

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
```
- 제휴를 맺은 보호소들이 보호중인 유기견의 리스트를 보여줍니다.
- 유기견 입양 리스트와 기능은 동일합니다.
### 입양되거나 찾은 유기견 삭제 메서드

- Find_manage_delete.java
```jsx
    //유기견 삭제 메서드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FindVO vo = new FindVO();
		FindService fService = new FindService();
		ServletContext context = getServletContext();
		String no = request.getParameter("no");
		vo.setDesertionNo(no);
		
		String path = context.getRealPath("upload\\dog"); 
		fService.FindDeleteFileName(vo);
		String filepath = vo.getFilename();
		filepath.substring(11);
		System.out.println("filepath : "+filepath);
		
		System.out.println(path);
		File f = new File(path +"\\"+filepath);
		if(f.exists()){
			f.delete();
			System.out.println("파일 삭제됨");
		}else{
			System.out.println("파일 없음");
		}
		
		
		int result = fService.FindDelete(vo);
		System.out.println(result);
		
		if(result==1) {
			PrintWriter out = response.getWriter();
			out.println("<script>alert('삭제가 완료 되었습니다.')</script>");
			response.sendRedirect("fine_find_manage_List.do");
		}else {
			PrintWriter out = response.getWriter();
			out.println("<script>alert('삭제를 실패 하였습니다.')</script>");
			response.sendRedirect("fine_find_manage_List.do");
		}
	
	
	}
```

- FindDAO.java
```jsx
//유기견 주인이 찾은 유기견 삭제 메서드
	public int findDelete(Connection conn, FindVO vo, PreparedStatement pstmt, ResultSet rs) {
		int result = 0;
		String sql = "delete from dog where desertionNo = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getDesertionNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}
```
- 자신의 보호소 안에 있는 유기견이 입양되거나 주인을 찾는다면 관리자가 해당 유기견의 정보를 삭제하는 메서드 입니다.
### 보호소에 들어온 유기견 입력 

![manageWrite](https://user-images.githubusercontent.com/59170160/110350187-cc7a5e80-8076-11eb-81d0-35f6b74a9856.png)
- Find_fine_manage_write.java
```jsx
	//관리자가 직접 유기견의 정보 db인서트
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	
		
		
		
		String checkId = (String) request.getSession().getAttribute("sessionID");;
		
			if (!ServletFileUpload.isMultipartContent(request))
				response.sendRedirect("findHowMany.do");

			ServletContext context = getServletContext();
			String uploadPath = context.getRealPath(fileSavePath);
			System.out.println(uploadPath);
			MultipartRequest multi = new MultipartRequest(request, // request 객체
					uploadPath, // 서버 상 업로드 될 디렉토리
					uploadSizeLimit, // 업로드 파일 크기 제한
					encType, // 인코딩 방법
					new DefaultFileRenamePolicy() // 동일 이름 존재 시 새로운 이름 부여 방식
			);


			// 업로드 된 파일 이름 얻어오기
			String file = multi.getFilesystemName("uploadFile");
			System.out.println(file);

			String happenPlace = multi.getParameter("happenPlace");
			String age = multi.getParameter("age");
			String colorCd = multi.getParameter("colorCd");
			String happenDt = multi.getParameter("happenDt");
			String dogKind = multi.getParameter("dogKind");
			String neuterYn = multi.getParameter("neuterYn");
			String officetel = multi.getParameter("officetel");
			String orgNm = multi.getParameter("orgNm");
			String sexCd = multi.getParameter("sexCd");
			String specialMark = multi.getParameter("happenPlace");
			String weight = multi.getParameter("happenPlace");
			if (happenPlace != null && !happenPlace.equals("") && age != null && !age.equals("") && colorCd != null
					&& !colorCd.equals("") && happenDt != null && !happenDt.equals("") && dogKind != null
					&& !dogKind.equals("") && neuterYn != null && !neuterYn.equals("") 
					&&  officetel != null && !officetel.equals("") && orgNm != null
					&& !orgNm.equals("") && sexCd != null && !sexCd.equals("") && specialMark != null
					&& !specialMark.equals("") && weight != null && !weight.equals("")) {
				System.out.println("들어오냐?");
				FindService fService = new FindService();
				FindVO vo = new FindVO();
				vo.setHappenPlace(happenPlace);
				vo.setAge(age);
				vo.setColorCd(colorCd);
				vo.setHappenDt(happenDt);
				vo.setKindCd(dogKind);
				vo.setNeuterYn(neuterYn);
				vo.setOfficetel(officetel);
				vo.setOrgNm(orgNm);
				vo.setSexCd(sexCd);
				vo.setSpecialMark(specialMark);
				vo.setWeight(weight);
				System.out.println("vo저장");
				int result = fService.FindMangerWrite(checkId,vo, file);
				System.out.println(result);
				if (result == 1) {
					PrintWriter out = response.getWriter();
					out.println("<script>alert('등록이 완료 되었습니다.')</script>");

					response.sendRedirect("fine_find_manage_List.do");
				} else {
					RequestDispatcher disp = request.getRequestDispatcher("/view/find/fine_find_manage_List.jsp");
					disp.forward(request, response);
				}
			}
		}
	


}
```
- FindDAO.java
```jsx
//유기견 정보 입력
	public int FindMangerWrite(String checkId, FindVO vo, String file, Connection conn, PreparedStatement pstmt) {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		Date currentTime = new Date();
		String mTime = mSimpleDateFormat.format(currentTime);
		System.out.println(mTime);

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, +10);
		System.out.println("10일 뒤 날짜 : " + mSimpleDateFormat.format(cal.getTime()));
		int result = 0;
		String sql = "insert into dog(desertionno, care_name, care_adress, care_tel, happenPlace, age, colorcd, filename, happendt, dog_kind_no, neuteryn, noticeEdt, noticeSdt, officeTel, orgNm, popFile, processstate, sexcd, specialmark, weight)"
				+ "values(SEQ_dog_NO.NEXTVAL,(select care_name from care where care_no = (select care_no from member where id = ?)),(select adress from care where care_no = (select care_no from member where id = ?)),(select tel from care where care_no = (select care_no from member where id = ?))"
				+ ",?,?,?,'./upload/dog/" + file + "',?,(select dog_kind_no from dog_kind where kind = ?), ?, "
				+ mSimpleDateFormat.format(cal.getTime()) + "," + mTime
				+ ", (select phone from member where id=?),?,?,'보호중',?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, checkId);
			pstmt.setString(2, checkId);
			pstmt.setString(3, checkId);
			pstmt.setString(4, vo.getHappenPlace());
			pstmt.setString(5, vo.getAge());
			pstmt.setString(6, vo.getColorCd());
			pstmt.setString(7, vo.getHappenDt());
			pstmt.setString(8, vo.getKindCd());
			pstmt.setString(9, vo.getNeuterYn());
			pstmt.setString(10, checkId);
			pstmt.setString(11, vo.getOrgNm());
			pstmt.setString(12, "./upload/dog/"+file);
			pstmt.setString(13, vo.getSexCd());
			pstmt.setString(14, vo.getSpecialMark());
			pstmt.setString(15, vo.getWeight());
			result = pstmt.executeUpdate();
			if (result == 1) {
				return result;
			} else {
				System.out.println("유기견입력실패");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
```
- 보호소 관리자가 자신의 보호소로 들어온 유기견을 등록시키는 메서드입니다.
- MultipartRequest 로 form에 입력된 유기견의 사진을 가져와 DB에 저장하고 서버에 이미지를 등록하도록 구현하였습니다.




### 공공api에 새로 들어온 유기견 목록 가져와서 update

![manageUpdate](https://user-images.githubusercontent.com/59170160/110350184-cc7a5e80-8076-11eb-8a42-394d1d2fe80f.png)
- XmlParser.java
```jsx
public class XmlParser {
	private DocumentBuilderFactory documentBuilderFactory;
	private DocumentBuilder documentBuilder;
	private Document document;
	private NodeList nodeList;
	private NodeList nodeList2;

	public XmlParser(File file) {
		System.out.println(file + "파일보여줘");
		DomParser(file);
	}

	public void DomParser(File file) {
		try {
			documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(file);
			System.out.println("document : " + document);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<XmlDataVO> parse(String tagName) {
		List<XmlDataVO> listOfData = new ArrayList<XmlDataVO>();
		nodeList = document.getElementsByTagName(tagName);
		System.out.println(nodeList2);

	
		System.out.println("nodeList.getLength(): " + nodeList.getLength());
		

		
		for (int i = 0; i < nodeList.getLength(); i++) {

			Element element = (Element) nodeList.item(i);
//			String dog_kind_no = this.getTagValue("kindCd", element);
//			String kind = this.getTagValue("KNm", element);
			
			String desertionNo = this.getTagValue("desertionNo", element);
			String happenPlace = this.getTagValue("happenPlace", element);
			String age = this.getTagValue("age", element);
			String careAddr = this.getTagValue("careAddr", element);
			String careNm = this.getTagValue("careNm", element);
			String careTel = this.getTagValue("careTel", element);
			String color = this.getTagValue("colorCd", element);
			String filename = this.getTagValue("filename", element);
			String happenDt =this.getTagValue("happenDt", element);
			String kindCd =this.getTagValue("kindCd", element).substring(4);
			String neuterYn =this.getTagValue("neuterYn", element);
			String noticeEdt =this.getTagValue("noticeEdt", element);
			String noticeSdt =this.getTagValue("noticeSdt", element);
			String officetel =this.getTagValue("officetel", element);
			String orgNm =this.getTagValue("orgNm", element);
			String popfile =this.getTagValue("popfile", element);
			String processState =this.getTagValue("processState", element);
			String sexCd =this.getTagValue("sexCd", element);
			String specialMark =this.getTagValue("specialMark", element);
			String weight =this.getTagValue("weight", element);
//			
//			
//			XmlDataVO vo = new XmlDataVO(dog_kind_no, kind);
			XmlDataVO vo = new XmlDataVO(desertionNo, happenPlace, age,careAddr,  careNm, careTel,  color, filename, happenDt,
					kindCd, neuterYn, noticeEdt, noticeSdt, officetel, orgNm, popfile, processState, sexCd, specialMark, weight);
			listOfData.add(vo);
			System.out.println("");
		}
		return listOfData;
	}

	private String getTagValue(String tagName, Element element) {
		NodeList nodeList= null;
		if(element.getElementsByTagName(tagName).item(0).getChildNodes() == null) {
			nodeList = null;
		}else {
			nodeList = element.getElementsByTagName(tagName).item(0).getChildNodes();			
		}
		System.out.println(nodeList);
		Node node = nodeList.item(0);
        System.out.println(node.getNodeName());
		return node.getNodeValue();
		
	}
}

```
- Find_manage_data_update.java
```jsx
//유기견의 목록을 공공api에서 받아와서 xml파일로 변환 후 db에 입력하는 메서드
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
			
		String fileName = "C:\\eclips_java\\fine\\dogNotice.xml";
		BufferedReader br = null;

		// DocumentBuilderFactory 생성
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		String today = sdf.format(cal.getTime());

		try {
			// OpenApi호출
			int count = 0;
			String urlstr = "http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?serviceKey=z1Y%2B%2FKbcw8o3UyZuB3R%2BgSVOx1KhUHOFXWFNf9qVFHHj6bMePFYfs9cPFKx4L1CMZ6sFoWBAydrytd%2Br4aWySw%3D%3D&bgnde=20140601&endde="+today+"&upkind=417000&state=notice&pageNo=1&numOfRows=50000";
			URL url = new URL(urlstr);
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();

			// 응답 읽기
			br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
			String result = "";
			String line;
			while ((line = br.readLine()) != null) {
				result = result + line.trim();// result = URL로 XML을 읽은 값
			}

			// xml 파싱하기
			InputSource is = new InputSource(new StringReader(result));
			builder = factory.newDocumentBuilder();
			doc = builder.parse(is);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			XPathExpression expr = xpath.compile("//items/item");
			NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		
			BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, false));
			fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r");
			fw.write("\t\t<items>\r");
			for (int i = 0; i < nodeList.getLength(); i++) {
				System.out.println("<item>");
				NodeList child = nodeList.item(i).getChildNodes();
				fw.write("\t\t\t<item>\r");
				count++;
				for (int j = 0; j < child.getLength(); j++) {
					Node node = (Node) child.item(j);
					String as = "\t\t\t\t<" + node.getNodeName() + ">"
							+ node.getTextContent().replaceAll("&", "과").replace("과#", "ampS") + "</"
							+ node.getNodeName() + ">\r";
					fw.write(as);

				}


				fw.write("\t\t\t</item>\r");
			}
			fw.write("\t\t</items>");
			fw.flush();
			fw.close();
			System.out.println("생성!");
			System.out.println(count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		FindService fService = new FindService();
		int result = fService.insertNoticeSysDate();
		String ctxpath = request.getContextPath();
		if (result == 1) {
			System.out.println("DB저장성공");
		} else {
			System.out.println("db다시");
			
		}
		String ctx = request.getContextPath();
		PrintWriter out = response.getWriter();
		out.append("<script>alert('데이터를 최신화 하였습니다')</script>");
		out.println("<script>location.href='"+ctx+"/findHowMany.do'</script>"); 
		
	}

}
```
- FindDAO.java
```jsx
//새로 들어온 api정보 인서트
	public int insertNoticeSysDate(Connection conn, PreparedStatement pstmt) {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		Date currentTime = new Date();
		String mTime = mSimpleDateFormat.format(currentTime);
		System.out.println(mTime);

		int result = 0;
		File file = new File("C:\\eclips_java\\fine\\dogNotice.xml");
		XmlParser xmlParser = new XmlParser(file);
		List<XmlDataVO> tmp = xmlParser.parse("item");
		System.out.println("******tmp size: " + tmp.size());

//			  String sql = "insert into dog_kind(dog_kind_no, kind) values(?,?)";
		String sql = "insert into dog(desertionNo, happenPlace, age,   care_adress, care_name, care_Tel, colorCd, filename, happenDt,"
				+ "dog_kind_no, neuterYn, noticeEdt, noticeSdt, officetel, orgNm, popfile, processState, sexCd, specialMark, weight) "
				+ "values(SEQ_dog_NO.NEXTVAL, ?, ?,  ?, ?, ?, ?, ?, ?, (select distinct dog_kind_no from dog_kind where kind like ?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		int numOfRow = 0;
		try {
			for (int i = 0; i < tmp.size(); i++) {
//					  System.out.println("vo값:"+Integer.parseInt(tmp.get(i).getNoticeSdt()));
//					  System.out.println("오늘날자"+Integer.parseInt(mTime));
				pstmt = conn.prepareStatement(sql);
				if (Integer.parseInt(tmp.get(i).getNoticeSdt()) < Integer.parseInt(mTime)) {
					System.out.println("전에있던 데이터입니다. 건너뜀");
					continue;
				}
//				if (Integer.parseInt(tmp.get(i).getNoticeSdt()) == Integer.parseInt(mTime)) {
//					System.out.println("오늘날짜 데이터 입니다. 건너뜀");
//					continue;
//				}

				pstmt.setString(1, tmp.get(i).getHappenPlace());
				pstmt.setString(2, tmp.get(i).getAge());
				pstmt.setString(3, tmp.get(i).getCareAddr());
				pstmt.setString(4, tmp.get(i).getCareNm());
				pstmt.setString(5, tmp.get(i).getCareTel());
				pstmt.setString(6, tmp.get(i).getColorCd());
				pstmt.setString(7, tmp.get(i).getFilename());
				pstmt.setString(8, tmp.get(i).getHappenDt());
				pstmt.setString(9, tmp.get(i).getKindCd()); // 푸들 치와와
				pstmt.setString(10, tmp.get(i).getNeuterYn());
				pstmt.setString(11, tmp.get(i).getNoticeEdt());
				pstmt.setString(12, tmp.get(i).getNoticeSdt());
				pstmt.setString(13, tmp.get(i).getOfficetel());
				pstmt.setString(14, tmp.get(i).getOrgNm());
				pstmt.setString(15, tmp.get(i).getPopfile());
				pstmt.setString(16, tmp.get(i).getProcessState());
				pstmt.setString(17, tmp.get(i).getSexCd());
				pstmt.setString(18, tmp.get(i).getSpecialMark());
				pstmt.setString(19, tmp.get(i).getWeight());

				int r1 = pstmt.executeUpdate();
				numOfRow++;
				if (pstmt != null)
					pstmt.close();
				if (r1 == 1)
					System.out.println("insert ok");
				else {
					System.out.println("insert fail : " + r1);
					break;
				}
			}
			System.out.println("sucess to save" + numOfRow);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;

	}
```
- 먼저 저장될 XML파일을 지정합니다.
- 공공 api 데이터를 가져오기 위해 URL 메서드에 주소를 적고 HttpURLConnection 으로 연결을 하였습니다
- 이후 BufferedReader로 응답된 값을 읽은 후에 XPathExpression으로 어느노드를 읽을지 정하고 NodeList로 값을 읽어옵니다.
- 그 이후 NodeList하나 더 선언해 이 전 선언했던 NodeList의 자식노드들을 읽어와 BufferedWriter에 전에 지정한 파일에 적어주게 됩니다.
- 그리고 Dao에서 XmlParser로 넘길 파일을 정하고 넘겨줍니다.
- XmlParser에서 xml파일에 있는 노드들을 VO에 저장한 후 VO를 다시 호출하여 저장합니다.
- 저장할 때 DB에서 저장된 값을 모두 불러와 먼저 xml파일에 있는 데이터를 비교해 전에 있던 데이터라면 저장하지 않고 새로운 데이터만 저장하도록 구현하였습니다.
### 보호소 직원 입양현황 리스트 가져오기

![manageReservationLsit](https://user-images.githubusercontent.com/59170160/110350182-cbe1c800-8076-11eb-936e-8dcade9cabd0.png)
- fine_manage_reservationChck.java
```jsx
 //자신의 보호소의 입양예약 리스트 가져오기
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String ctx = request.getContextPath();
		String id = (String) request.getSession().getAttribute("sessionID");
		if(!request.getSession().getAttribute("memberLev").equals("2")) {
			PrintWriter out = response.getWriter();
			out.append("<script>alert('정상적인 접근이 아닙니다.')</script>");
			out.println("<script>location.href='"+ctx+"/findHowMany.do'</script>"); 
			out.flush();
			out.close();
		}else {
			List<ReserVO> list = new ArrayList<ReserVO>();
			FindService fService = new FindService();
			list = fService.getReservationInfo(id);
			if(list == null) {
				PrintWriter out = response.getWriter();
				out.append("<script>alert('예약정보가 없습니다')</script>");
				out.println("<script>location.href='"+ctx+"/findHowMany.do'</script>"); 
				out.flush();
				out.close();
			}else {
				request.setAttribute("list", list);
				RequestDispatcher disp = request.getRequestDispatcher("./view/find/fine_find_reservation_list.jsp");
						
				disp.forward(request, response);
				
			}
		}
		
	}
```
- FindDAO.java
```jsx
//입양 현황 불러오기
	public List<ReserVO> getReservationInfo(String id, Connection conn, DataSource ds, ResultSet rs,
			PreparedStatement pstmt) {
		List<ReserVO> list = new ArrayList<ReserVO>();
		String sql = "select * from reservation where care_no = (select care_no from member where id = ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				do {
					ReserVO vo = new ReserVO();
					vo.setResercationNo(rs.getString("RESERCATION_NO"));
					vo.setDesertionNo(rs.getInt("DESERTIONNO"));
					vo.setId(rs.getString("ID"));
					vo.setCareNo(rs.getString("CARE_NO"));
					vo.setReservationDate(rs.getString("RESERVATION_DATE"));
					list.add(vo);
				}while(rs.next());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
```
- 관리자 페이지에서 유기견을 입양 예약한 정보를 불러오는 메서드 입니다.
- 예약 리스트가 비어있다면 PrintWriter 입양 예약현황이 없다는 경고창의 띄워주도록 코딩하였습니다.
### 메인 페이지 한주간 유기된 유기견 리스트
![main](https://user-images.githubusercontent.com/59170160/110350181-cbe1c800-8076-11eb-9cb5-26bc0eb54e01.png)
- Find_HowMany.java
```jsx
 //메인페이지에 띄워줄 일주일간 유기된 유기견의 목록과 수
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		FindService fService = new FindService();
		List<FindVO> list = new ArrayList<FindVO>();
		int count = fService.getHowManyCount();
		list = fService.getHowManyLsit();
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		RequestDispatcher disp = request.getRequestDispatcher("./view/main/index.jsp");
		disp.forward(request, response);
	}

```
- FindDAO.java
```jsx
//메인페이지 한주간 유기된 유기견의 수
	public List<FindVO> getHowManyLsit(Connection conn, DataSource ds, ResultSet rs, PreparedStatement pstmt) {
		List<FindVO> list = new ArrayList<FindVO>();
		String sql = "select * from(select rownum rnum, d.* from "
				+ "(select * from dog where dog_kind_no in (select dog_kind_no from dog_kind) and noticeEdt > '20201202') d) where rnum >= 1 and rnum <= 32";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				do {
					FindVO vo = new FindVO();
					vo.setDesertionNo(rs.getString("desertionNo"));
					vo.setHappenPlace(rs.getString("happenplace"));
					vo.setAge(rs.getString("age"));
					vo.setCareAddr(rs.getString("care_adress"));
					vo.setCareNm(rs.getString("care_name"));
					vo.setCareTel(rs.getString("care_tel"));
					vo.setColorCd(rs.getString("colorcd"));
					vo.setFilename(rs.getString("filename"));
					vo.setHappenDt(rs.getString("happendt"));
					vo.setKindCd(rs.getString("DOG_KIND_NO"));
					vo.setNeuterYn(rs.getString("neuteryn"));
					vo.setNoticeEdt(rs.getString("noticeEdt"));
					vo.setNoticeSdt(rs.getString("noticeSdt"));
					vo.setOfficetel(rs.getString("officetel"));
					vo.setOrgNm(rs.getString("orgnm"));
					vo.setPopfile(rs.getString("popfile"));
					vo.setProcessState(rs.getString("processstate"));
					vo.setSexCd(rs.getString("sexcd"));
					vo.setSpecialMark(rs.getString("specialMark"));
					vo.setWeight(rs.getString("weight"));
					list.add(vo);
				}while(rs.next());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}

```
index.jsp
```jsx
	<c:if test="${not empty count }">
	      <div class="temporary2">최근 한주간 유기된의 유기견의수는 ${count }마리입니다.</div>
	</c:if>
	      <div class="temporary3">
	<c:if test="${not empty list }">
		<c:forEach items="${list }" var="mvo" step="1">
			<div class="dogPic"><a href="<%=ctxPath%>/findDetail.do?no=${mvo.desertionNo}"><img src="${mvo.popfile }" style="width:380px; height: 350px;"></a></div>
		</c:forEach>
      	</c:if>
- 한주동안 유기된 유기견의 수를 가져와 화면에 뿌려주고 유기견의 사진을 들고와 보여주도록 jstl을 활용하여 구연하였습니다.
- 사진을 누르면 해당 유기견의 상세 정보를 볼 수 있도록 상세 페이지로 이동하게 구현하였습니다.

