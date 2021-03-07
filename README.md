# fine semiPrj
## :pushpin: DESK APP :pushpin:
협업을 위한 그룹웨어

2020.12 ~ 2021.01

## :mag: Project

#### 개요
사내에서 사용하는 업무메신저를 확장하여 각각의 프로젝트에 인원을 추가할수 있고 진행상황과 서로의 스케줄을 공유하기 위해 만들었습니다. 

#### 주요기능
- 실시간 접속여부확인
- 1대1채팅과 알림 전송
- 프로젝트 추가, 수정, 열람 권한부여
- 로그인 아이디는 일정한 규칙으로 자동 부여

##  👀 기술스택
![캡처](https://user-images.githubusercontent.com/69295153/106429174-6fa2eb80-64ad-11eb-9810-c02000325b36.PNG)

#### 그 외 API

| 라이브러리                      | 버전       |
| ------------------------------ | ---------- |
| jstl                           | 1.2        |
| JSON-simple                    | **^16.11** |
| JdataPicker                    | **^5.1**   |
| Spring WebSocket               | **3.3**    |
| Lombok Maven Plugin            | **^4.4**   |
| Mybatis Spring                 | **3.6**    |
| ojdbc6                         | **4.1.5**  |
| Apache commons Fileupload      | **4.1.5**  |
| Naver Smart editer             | 2.2        |

## 👩‍💻 Member

#### 오승하
- 전자결재 기능 전체 (결재 작성, 결재 승인, 결재 분류)
- 스프링 웹소켓을 활용한 기능 전체 ( 프로젝트 추가 알림보내기, 수신 받은 결재 알림보내기, 채팅 알림보내기 )
- ajax를 활용하여 DB에 있는 멤버테이블 주소록 전체 불러오기 

## 🔧설정 파일

#### header.js
 ```jsx
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async
	src="https://www.googletagmanager.com/gtag/js?id=UA-119386393-1"></script>
<script>
	window.dataLayer = window.dataLayer || [];
	function gtag() {
		dataLayer.push(arguments);
	}
	gtag('js', new Date());
	gtag('config', 'UA-119386393-1');
</script>
```
gtag로 화면 전환시 로딩 페이지를 보여주었습니다. 

 ```jsx
 function connectWS() {
		console.log("qqqqqqqqqqq")
		var ws = new WebSocket("ws://localhost:8090/wefer/replyEcho");
		console.log("qqqqqqqqqqq 연결됨")
		socket = ws;
		ws.onopen = function() {
			console.log('Info: connection opened.');
		};
		ws.onmessage = function(event) {
			console.log("ReceiveMessage:", event.data + '\n');
		if ((event.data + '\n').length <= 9){
			console.log(event.data + '\n');
         var login_status_name = "#login_status_"+event.data;
         console.log("지금 로그인중인사람" +login_status_name);
         // 주소록에서 로그인 된 사람 이름을 녹색
         $(login_status_name).css('background-color','green');
         // 아니면 원래 색인 빨강 그대로 유지
		}else {
			var $socketAlert = $('ul#socketAlert');
			$socketAlert.html(event.data);
			$('#dot').css('display','block');
			$socketAlert.css('display', 'block');
 		var $socketMessage = $('div#socketMessage'); 
			$socketMessage.html(event.data); 
 			$socketMessage.css('display', 'block'); 
		     setTimeout( function() {
			       $socketMessage.css('display', 'none');
			    }, 3000);  
		};
		}
		ws.onclose = function(event) {
			console.log('Info: connection closed.');
		};
		ws.onerror = function(err) {
			console.log('Error:', err);
		};
	}
 ```
 헤더에 웹소켓을 두어 모든 페이지가 로딩 될때마다 소켓을 커넥션하도록 했습니다. 
 if문을 사용하여 연결된 소켓이 로그인을 한 사용자인지, 수신참조되어 알림이 가야하는 사용자인지 구별을 했습니다. 
 알림이 가는 경우 타임아웃을 3000로 주어 3초동안 알림이 떴다 사라지지만, li태그 안에서는 확인할때까지 유지하도록 했습니다. 

 ```jsx
  $.ajax({
            url : "${pageContext.request.contextPath}/memeberList",
            type : "POST",
            contentType : "application/json; charset=utf-8;",
            dataType : "json",
            success : function(data) {
               for (var i = 0; i < data.length; i++) {   // 주소록
                  if (data[i].dept_name == '인사팀') {
                     if ($('.insa').length == 0) {
                        $('.result').append( '<ul class="insa"><span  style="color:white;">'  + data[i].dept_name  + '</span></ul>');
                     }
                  } else if (data[i].dept_name == '경영팀') {
                     if ($('.gyeonyoung').length == 0) {
                        $('.result').append( '<ul class="gyeonyoung"><span style="color:white;">'  + data[i].dept_name + '</span></ul>');
                     }
                  } else if (data[i].dept_name == '개발팀') {
                     if ($('.geabal').length == 0) {
                        $('.result').append( '<ul class="geabal"><span style="color:white;">'  + data[i].dept_name  + '</span></ul>');
                     }
                  }
               }
               for (var i = 0; i < data.length; i++) {
                  if (data[i].dept_name == '인사팀') {
                     if (data[i].status == '퇴근') {
                        $('.insa').append( '<li value = "' + data[i].name + '" ><a href="#" onclick="location.href=\'/wefer\/personerProfileList.do?addrMemberId='
                                          + data[i].id + '\'"><img src="${pageContext.request.contextPath}/resources/profileImg/'+data[i].profile+'" style="width:30px; heigh:30px;"> ' + data[i].name  + '('  + data[i].position + ')<div id="login_status_'+data[i].name+'" style="width:7px; height:7px; border-radius:50%; background-color:red;"></div></a></li>');
                     } else if (data[i].status == '출근') {
                        $('.insa').append( '<li value = "' + data[i].name + '" ><a href="#" onclick="location.href=\'/wefer\/personerProfileList.do?addrMemberId='
                                          + data[i].i
                                         -----생략----
				},
				error : function() {
					alert("restController err");
				}
			});
 ```
 ajax로 모든 사용자들의 주소록을 불러왔습니다. 소켓이 연결된 사용자인지 구별하여 온/오프라인 상태를 css를 통해 색을 다르게 지정하였습니다. 


#### apprdetail.jsp
 ```jsx
<div class="container">
		<h6 class="weight-400 d-flex">
		<i class="icon-copy dw dw-checked mr-2" data-color="#1b00ff"></i>
		<c:if test="${payment_id.conference.conference_title != null}">
		${payment_id.conference.conference_title}
</c:if>
									
<c:choose>
	<c:when
	test="${payment_id.conference.conference_contents == null}">
	<td>${payment_id.annual_content}</td>
	</c:when>
	<c:otherwise>
		<td>${payment_id.conference.conference_contents}</td>
	</c:otherwise>
        </c:choose>
	</h6>
</div> 
 ```
 jstl 사용하여 view에 출력하였습니다. 또한 if문에 해당하는 c태그를 사용하여 값 제어를 했습니다.  

#### apprform.jsp
 ```jsx
		$('#prj-add-project').on('click', function(evt) {
			console.log('수신자 클릭하면');
			var member_list = $(".setting-name").get();
			console.log("member_list.length:" + member_list.length);
			var members_id = $(
			"input[name=members_id][type=hidden]")
			.get();
			console.log("members_id.length:" + members_id.length);
			for (var i = 0; i < member_list.length; i++) {
				console.log("$(member_list[i]).val(): " + $(member_list[i]).val());
				var memberId = $(members_id[i]).val();
				console.log("memberId" + memberId);
				if (socket) {
					var socketMsg = "전자결재," + memberId;
					console.log("sssssssmsg>>", socketMsg)
					socket.send(socketMsg);
				}
			 else {
				console.log("Error on editReply>>", res);
			}
			
		};
		});
 ```
 전자결재를 승인해 줄 승인 참조자에게 알림을 보내기 위해 소켓을 사용한 코드입니다. 

 #### chat.jsp
  ```jsx
  		<!-- 웹소켓 js -->
		<script type="text/javascript">
			var ws;
			var messages = document.getElementById("messages");
			var flag = true;
			function openSocket(name, photo) {
				$('#yes').css('display', 'block');
				$('#no').css('display', 'none');
				var c = confirm(name+"님과 채팅을 시작하시겠습니까?");
				var chatM = $('.name').append('<h3 id = "name">'+name+'</h3>');
					$('.photo').append('<img src="${pageContext.request.contextPath}/resources/profileImg/'+photo+'" style="width:50px; height:50px;">'); 
					var chatmem = "대화에 초대," + "[" +name+ "]";
					console.log("chhhhhhhhhhasmsg>>", chatmem)
					socket.send(chatmem);
				if (ws !== undefined && ws.readyState !== WebSocket.CLOSED) {
					writeResponse("대화는 한명씩만 가능합니다. 대화를 먼저 종료해주세요.");
					return;
				}
				//웹소켓 객체
				ws = new WebSocket("ws://localhost:8090/wefer/wecho.do");
				ws.onopen = function(event) {
					if (event.data === undefined) {
						return;
					}
					writeResponse(event.data);
				};
				ws.onmessage = function(event) {
					console.log(event.data)
					writeResponse(event.data);
				};
				ws.onclose = function(event) {
					writeResponse("상대방이 대화를 종료했습니다.");
					 function clearText(){
				            console.log(messages.parentNode);
				            messages.parentNode.removeChild(messages)
				      	}
				}
			}
			function enterkey() {
		        if (window.event.keyCode == 13) {
		        	sent()
		        }
		}
			function sent() { // 보내는 사람만 들어감 
				var today = new Date();  
				var text = document.getElementById("messageinput").value 
				ws.send(text);
				document.getElementById("messageinput").value = "";
				console.log("sent에 들어오면 보내는거 받는사람은 안들어옴 " + text);
				if (flag == true){
					messages.innerHTML +=  // 오른쪽에 나와야함 
					"<li class=\"clearfix admin_chat\" style = \"list-style: none;\"><span class=\"chat-img\">\r\n" + 
	        		"<img src=\"vendors/images/chat-img2.jpg\" style = \"display: none;\" alt=\"\">\r\n" + 
	        		"</span>\r\n" + 
	        		"<div class=\"chat-body clearfix\"><p>"+ text +"</p><div class=\"chat_time\">"+today.toLocaleTimeString()+"</div>\r\n" + "</div></li>"
	        		flag = false;
					  $("#scroll").scrollTop($("#scroll")[0].scrollHeight);
					  console.log($("#scroll")[0].scrollHeight);
				}
			}
			function writeResponse(text) {
				var today = new Date();   
				var sessionid = "${loginName}";  // 로그인한 사람
				var sender =  document.getElementById("sender").value; // 지금 글 보낸 사람 
				if (sessionid == sender && flag == true){
				console.log(today.toLocaleTimeString());	
					messages.innerHTML +=  // 왼쪽
						"<li class=\"clearfix \" style = \"list-style: none;\"><span class=\"chat-img\">\r\n" + 
		        		"<img src=\"vendors/images/chat-img2.jpg\" style = \"display: none;\" alt=\"\">\r\n" + 
		        		"</span>\r\n" + 
		        		"<div class=\"chat-body clearfix\"><p style = \"display: inline-block;\">"+ text +"</p><div class=\"chat_time\">"+today.toLocaleTimeString()+"</div>\r\n" + "</div></li>"
						  $("#scroll").scrollTop($("#scroll")[0].scrollHeight);
				}flag = true;
			}
			function closeSocket() {
				var c = confirm("채팅창을 닫겠습니까?");
		   
				$('.photo').empty();
				$('.name').empty();
				if (c){
					$('#yes').css('display', 'none');
					$('#no').css('display', 'block');
					
					ws.onclose();
				}
		}
		</script>
  ```
 주고받는 메세지를 출력하는 소스입니다. 핸들러를 통해 writeResponse함수안으로 들어오는 모든 메세지는 한꺼번에 출력되어,
 받은 메세지와 보낸 메세지를 구별하는 것이 어려웠습니다. 따라서 flag를 사용해 flag가 flase인경우에는 writeResponse에 메세지가 들어오더라도 출력이 되지 않도록 구현했습니다.
 또한 Date()객체를 사용해 현재시간을 출력하였습니다. 

#### DB 
 ![Display_3](https://user-images.githubusercontent.com/69295153/106435685-8bf75600-64b6-11eb-81c4-ad88c39d3b55.png)
 payment_confirm테이블과 payment_comment테이블은 payment테이블의 payment_id가 외래키로 있으며 해당 게시글을 들어갔을때 출력되는 테이블입니다. 
 annual테이블과 conference테이블은 작성자 id로 외래키가 걸려있으며, 작성자 id는 멤버테이블의 컬럼입니다. 
 anuual테이블 혹은 conference테이블에서 글을 작성했을시 작성자 id가 payment테이블의 id에 등록되며, 자동으로 payment_id시퀀스가 증가합니다. 
 payment를 작성했을시 결재를 참조해줄 참조자 3인이 payment_confirm테이블의 S_member에 각각 들어가며, 한명이 승인을 해줄때마다 confrim의 값이 +2가되어 confirm 컬럼의 값이 6이되면
 payment의 status가 진행중에서 승인으로 update됩니다.  

```jsx
<select id="paymentList" resultType="arraylist"
		parameterType="string" resultMap="resultPayment">
		select c.conference_title, p.id,
		p.payment_item, p.payment_status,c.conference_date, p.conference_id, p.annual_id, p.payment_id, p.write_date,m.name
		from payment p
		left outer
		join conference c
		on
		c.conference_id = p.conference_id
		left outer join
		annual a
		on
		a.annual_id = p.annual_id
		left outer join member m
		on
		m.id = p.id
		where exists (select 1
		from member
		where member.id = p.id
		and member.id = #{id})
	</select>
	<select id="receive-paymentList" resultType="arraylist"
		parameterType="string" resultMap="resultPayment">
		select c.conference_title, p.id,
		p.payment_item, p.payment_status,c.conference_date, p.conference_id, p.annual_id, p.payment_id, p.write_date,m.name
		from payment p
		left outer join
		conference c
		on
		c.conference_id = p.conference_id
		left outer join
		annual a
		on
		a.annual_id = p.annual_id
		left outer join member m
		on
		m.id = p.id
		where p.payment_id in (select pc.payment_id
		from payment_confirm pc
		where pc.S_MEMBER_ID0 = #{name}
		or pc.S_MEMBER_ID1 =#{name} or
		pc.S_MEMBER_ID2 =#{name}
		)
	</select>
```
로그인한 사용자가 작성하거나, 수신받은 결재리스트를 뽑아오는 쿼리문입니다. paymentList에서는 현재 로그인한 사용자가 발신한 모든 결재를 출력하며, receive-paymentList에서는 payment-confirm테이블에 로그인한 사용자의 이름이 하나라도 있는 경우 출력하는 쿼리문입니다. 

```jsx
	<update id="confirmCnt" parameterType="Payment"
		statementType="PREPARED">
		update
		payment_confirm set payment_confirm.confirm =
		payment_confirm.confirm+(
		SELECT sum (a)
		from
		(select
		(count(S_MEMBER_ID0)) as a from payment_confirm where s_member_id0 =
		#{name} and payment_id=#{payment_id}
		union all
		select
		(count(S_MEMBER_ID1))
		from payment_confirm where s_member_id1 =
		#{name}
		and payment_id=#{payment_id}
		union all
		select
		(count(S_MEMBER_ID2))
		from payment_confirm where
		s_member_id2 =
		#{name} and
		payment_id=#{payment_id}))where
		payment_id=#{payment_id}
	</update>
	
	<update id="status" parameterType="Payment"
		statementType="PREPARED">
		update payment set payment_status = (select case when
		confirm >= 6 then '승인'
		else '진행중' end
		from (select confirm from
		payment_confirm
		where payment_id=#{payment_id} )) where
		payment_id=#{payment_id}
	</update>
```
결재 승인여부를 확인하고 상태를 변경하는 쿼리문입니다. S_member가 한명씩 승인할때마다 2씩카운트를하며 전체의 총합이 6이되면 진행중에서 승인으로 변경되도록 했습니다. 
```jsx
<insert id = "annualInsert" parameterType="Annual"
		flushCache="true" statementType="PREPARED">
      <selectKey resultType="string" keyProperty="id" order="BEFORE">
         select id from member where id = #{id}
      </selectKey>
		insert into annual (annual_id, annual_content, annual_stddate, annual_enddate, annual_kind,id)
		values (#{annual_id},
		#{annual_content},
		#{annual_stddate},
		#{annual_enddate},
		#{annual_kind},
		#{id})
	</insert>
	<select id = "seqAnnualPayment" resultType="string">
		select SEQ_PAYMENT_ANNUAL.nextval from dual
	</select>
```
annual인서트시, 로그인한 사용자의 아이디 값과, 외래키로 적용되어있는 payment_id를 모두 파라메터로 들고가야하는 애로사항이 있었습니다. 
따라서 insert시 SEQ_PAYMENT_ANNUAL 시퀀스만을 select한후 아래와 같이 DAO와 Sevice에서 적용시켰습니다. 
#### AnnualDao.java
```jsx
	public String seqAnnualPayment() {
		String seqAnnualPayment = sqlSession.selectOne("AnnualMapper.seqAnnualPayment");
		return seqAnnualPayment;
	}
```
AnnualService.java
```jsx
public int insertAnnualPayment(Annual a, Payment b) {
		String seq = aDao.seqAnnualPayment();
		a.setAnnual_id(seq);
		b.setAnnual_id(seq);
		
		String seqc = pmDao.seqPayment();
		b.setPayment_id(seqc);
		
		int resultA = aDao.insertAnnual(a);
		int resultB= pmDao.insertPayment(b);
		int resultC= pcDao.insertPaymentConfirm(b);
		
		if(resultA==1 || resultB==1 || resultC==1)
			return 1;
		else
			return 0;
	}
```
#### EchoHandler.java
```jsx
public class ReplyEchoHandler extends TextWebSocketHandler {
	// 모든 사용자
	List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	// 로그인중인 개별유저
	Map<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();
	// 클라이언트가 서버로 연결시
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("커넥션됐니  :" + session);
		sessions.add(session); // 로그인한 세션아이디가 들어감
		log(session.getId() + "연결됨 / 알림소켓 ");
		String senderId = getMemberId(session); 
		if (senderId != null) { // 로그인 값이 있는 경우만
			log(senderId + " 연결 됨");
			users.put(senderId, session); // 로그인중인 유저 저장
			for (WebSocketSession sess : sessions) {
					sess.sendMessage(new TextMessage(senderId));
			}
		}
	}
	// 클라이언트가 Data 전송 시
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("핸들러 텍스트 메세지 :" + session + ":" + message.getPayload());
		String sender = getMemberId(session);
		String msg = message.getPayload();
		if (msg != null) {
			String[] strs = msg.split(",");
			if (strs != null && strs.length == 2) {
				String cmd = strs[0];
				String target = strs[1]; // m_id 저장
				log(cmd);
				log(target);
				for (WebSocketSession sess : sessions) {
					if (cmd.equals("대화에 초대")) {
						sess.sendMessage(new TextMessage("<li><a href='./chat.do' style = \"color: blue;\" >"
								+ sender + "님이 " + cmd + "했습니다</a></li>"));
					}
					WebSocketSession targetSession = users.get(target); // 수신을 받을 세션 
					// 실시간 접속
					if (targetSession != null) {
						System.out.println("실시간 접속했나");						
						if (true) {
							if (cmd.equals("전자결재")) {
								TextMessage tmpMsg = new TextMessage("<li><a href='./approval.do' style = \"color: blue;\" >"
										+ sender + "님이 " + cmd + "를 등록했습니다</a></li>");
								targetSession.sendMessage(tmpMsg);
								
							}else {
								TextMessage tmpMsg = new TextMessage("<li><a href='./projectlist.do' style = \"color: blue;\" >"
										+ sender + "님이 " + cmd + "를 등록했습니다</a></li>");
								targetSession.sendMessage(tmpMsg);
							}
						}
					}
				}
			}
		}
	}
	// 웹소켓에 id 가져오기
	// 접속한 유저의 http세션을 조회하여 id가져오기
	private String getMemberId(WebSocketSession session) {
		Map<String, Object> httpSession = session.getAttributes();
		String m_name = (String) httpSession.get("loginName"); // 세션에 저장된 m_id 기준 조회
		if (m_name == null)
		return m_name == null ? null : m_name;
	}
```
사용자가 접속했다면 모든 사용자에게 접속중임을 알리며, 수신 참조자로 선택됐을시에는 해당 사용자에게만 알림을 보내는 소스 코드입니다. 