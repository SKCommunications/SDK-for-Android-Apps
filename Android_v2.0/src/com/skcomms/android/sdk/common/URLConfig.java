package com.skcomms.android.sdk.common;

/**
 * Nate OpenAPI 들의 URL 정의
 * @link http://devsquare.nate.com
 */
public class URLConfig {

	/**
	 * Request Token 획득 URL
	 */
	public static final String NATE_OPENAPI_GET_REQUEST_TOKEN = "https://oauth.nate.com/OAuth/GetRequestToken/V1a";

	/**
	 * Authorize URL
	 */
	public static final String NATE_OPENAPI_AUTHORIZED_URL = "https://oauth.nate.com/OAuth/Authorize/V1a";

	/**
	 * Access Token 획득 URL
	 */
	public static final String NATE_OPENAPI_GET_ACCESS_TOKEN = "https://oauth.nate.com/OAuth/GetAccessToken/V1a";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 대상 싸이월드의 기본홈정보를 보여 줍니다. 조회하는 싸이월드 아이디와 로그인 한 사람의 관계를 알 수 있으며, 
	 * 대상 싸이월드가 본인의 싸이월드인 경우에는 추가적인 연동정보(현재, 트위터 연동정보만 제공함)를 보여줍니다. 
	 * response 중에 id 값은 미니홈피/일촌/노트 API 호출시 request 변수 중 회원KEY값(targetId)으로 사용합니다.
 	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), profileThumb: 프로필이미지썸네일싸이즈(0= 원본, 1= 45x45, 2= 65x65, 3= 80x80, 4= 150x150),
 	 * output: 응답규격(xml,json) 
 	 * @params_example : targetId=&profileThumb=1&output=xml
	 * @return XML/JSON String
	 */
	public static final String MINIHOME_GET_HOMEINFO = "https://openapi.nate.com/OApi/RestApiSSL/CY/200800/gethomeinfo/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 각 메뉴(다이어리, 사진첩, 게시판 등) 중 닫혀있는 메뉴를 오픈합니다. 열려있는 메뉴를 닫는 기능은 제공하지
	 *       않습니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), menuType: 메뉴타입지정 (게시판 : “1” 또는 “board” ,
	 *         사진첩 : “2” 또는 “photo”, 방명록 : “11” 또는 “visitbook”, 다이어리 : “12” 또는 “diary” )
	 * @params_example targetId=&menuType=photo
	 * @return XML String
	 */
	public static final String MINIHOME_OPEN_MENU = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_ModifyMenuOpen/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 각 메뉴(다이어리, 사진첩, 게시판)의 폴더 목록을 보여 줍니다. 폴더의 공개 설정과 일촌 여부에 맞는 결과값을
	 *       보여줍니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), menuType: 메뉴타입지정 (게시판 : “1” 또는 “board” , 
	 *         사진첩 : “2” 또는 “photo”, 방명록 : “11” 또는 “visitbook”, 다이어리 : “12” 또는 “diary” )
	 * @params_example targetId=&menuType=photo
	 * @return XML String
	 */
	public static final String MINIHOME_GET_FOLDERLIST = "http://openapi.nate.com/OApi/RestApi/CY/200110/xml_RetrieveFolderList/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 각 메뉴(게시판, 사진첩, 방명록, 다이어리)의 오픈 여부를 알려줍니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), menuType: 메뉴타입지정 (게시판 : “1” 또는 “board” , 
	 *         사진첩 : “2” 또는 “photo”, 방명록 : “11” 또는 “visitbook”, 다이어리 : “12” 또는 “diary” )
	 * @params_example targetId=&menuType=photo
	 * @return XML String
	 */
	public static final String MINIHOME_GET_MENUOPEN = "http://openapi.nate.com/OApi/RestApi/CY/200110/xml_RetrieveMenuOpen/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 사진첩의 사진 목록 정보를 불러오는 API입니다. 게시물의 공개 설정과 일촌 여부에 맞는 결과값을 보여줍니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), folderNo: 폴더번호(전체보기:0),
	 *         cPage: 요청페이지번호, perPage: 요청게시물수(범위:4~20), searchType: <검색 타입 지정>
	 *         검색않음 : “0” 또는 “none” 제목검색 : “1” 또는 “title” 태그검색 : “2” 또는 “tag”,
	 *         searchWord: 검색어
	 * @params_example targetId=&folderNo=2&cPage=1&perPage=10&searchType=title&searchWord=일상
	 * @references MINIHOME_GET_FOLDERLIST
	 * @return XML String 
	 */
	public static final String MINIHOME_GET_ALBUM_ITEMS = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RetrievePhotoItemList/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 사진첩 중 하나의 사진에 대한 상세 정보를 불러오는 API입니다. 게시물의 공개 설정과 일촌 여부에 맞는 결과값을 보여줍니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq : 사진첩게시물키 
	 * @params_example targetId=&itemSeq=12345678
	 * @references MINIHOME_GET_ALBUM_ITEMS
	 * @return XML String
	 */
	public static final String MINIHOME_GET_ALBUM_ITEM = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RetrievePhotoItem/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 사진첩에 새로운 게시물을 작성하는 API입니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), folderNo: 폴더번호(전체보기:0),
	 *         title: 제목, content: 사진첩 내용, itemOpen: <게시물 공개 설정> 비공개 : “0” 또는
	 *         “secret” 일촌공개 : “1” 또는 “oneDegOpen” 전체공개 : “4” 또는 “allOpen”,
	 *         photoUrls: 첨부파일URL(사진업로드 API 호출을 통해 얻은 결과), isScrapOpen:
	 *         스크랩허용여부(true/false), isSearchOpen: 검색허용여부(true/false)
	 * @params_example 
	 *                 targetId=&folderNo=2&title=2012년10월30일의일상&content=오늘의하루&photoUrls
	 *                 =/s16601/2004/11/15/34/사진
	 *                 012.jpg&isScrapOpen=false&isSearchOpen=false
	 * @references MINIHOME_UPLOAD_PHOTO, MINIHOME_GET_FOLDERLIST
	 * @return XML String
	 */
	public static final String MINIHOME_CREATE_ALBUM_ITEM = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RegisterPhotoItem/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 사진첩에 게시물에 첨부될 이미지 파일을 업로드 하는 API입니다.
	 * @params multipart/form-data 로 file 첨부 (name="file")
	 * @params_example HTML의 경우 <input type="file" name="file" id="Cy_Photo_upload_file" value="" size="60" />
	 * @references MINIHOME_CREATE_ALBUM_ITEM
	 * @return XML String
	 */
	public static final String MINIHOME_UPLOAD_PHOTO = "http://openapi.nate.com/OApi/CyPhotoRestApi/V1";
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 사진첩의 게시물 제목을 수정하는 API입니다. 본인이 작성한 사진첩 게시물만 제목 수정이 가능합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq : 사진첩게시물키, title: 수정할 제목
	 * @params_example targetId=&itemSeq=42391847&title=제목수정
	 * @references MINIHOME_GET_ALBUM_ITEMS
	 * @return XML String
	 */
	public static final String MINIHOME_MODIFY_ALBUM_ITEM_TITLE = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_ModifyPhotoItemTitle/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 사진첩 게시물의 공개설정(전체공개/일촌공개/비공개)을 변경하는 API입니다. 본인이 작성한 사진첩 게시물만 제목
	 *       수정이 가능합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq : 사진첩게시물키,
	 *         itemOpen: 게시물 공개 설정 (비공개 : “0” 또는 “secret” 일촌공개 : “1” 또는
	 *         “oneDegOpen” 전체공개 : “4” 또는 “allOpen”)
	 * @params_example targetId=&itemSeq=42391847&itemOpen=secret
	 * @references MINIHOME_GET_ALBUM_ITEMS
	 * @return XML String
	 */
	public static final String MINIHOME_MODIFY_ALBUM_ITEM_OPENTYPE = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_ModifyPhotoItemOpen/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 사진첩 게시물을 사진첩 내 다른 폴더로 이동시키는 API입니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq : 사진첩게시물키, tFolderNo : 이동할 폴더 번호.
	 * @params_example targetId=&itemSeq=42391847&tFolderNo=4
	 * @references MINIHOME_GET_ALBUM_ITEMS, MINIHOME_GET_FOLDERLIST
	 * @return XML String
	 */
	public static final String MINIHOME_MOVE_ALBUM_FOLDER = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_ModifyPhotoItemFolderMove/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 사진첩 게시물의 댓글 목록 정보를 불러오는 API 입니다. 게시물의 공개 설정과 일촌 여부에 맞는 결과값을 보여줍니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq : 사진첩게시물키, cPage : 요청 댓글 페이지 번호, perPage: 페이지 당 요청 댓글 수 (범위:30~50)
	 * @params_example targetId=&itemSeq=42391847&cPage=1&perPage=35
	 * @references MINIHOME_GET_ALBUM_ITEMS
	 * @return XML String
	 */
	public static final String MINIHOME_GET_ALUBM_ITEM_REPLYS = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RetrievePhotoReplyList/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 사진첩 게시물 또는 댓글에 대하여 댓글 또는 재댓글을 작성하는 API입니다. 댓글 허용 설정 및 일촌 여부에
	 *       따라 댓글 작성 가능 여부가 결정됩니다. 1) 댓글에 대한 재댓글을 작성하고자 할 때에는 parentReplySeq
	 *       필드에 해당 댓글 키를 넣으면 됩니다. (itemSeq=parentReplySeq 일 때는 일반 댓글로 작성됩니다.)
	 *       2) visitIP 정보가 없을 경우에는 모바일로 인식하여, 미니홈피 댓글 이름UI 앞에 모바일 아이콘이 노출됩니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq : 사진첩게시물키,
	 *         parentReplySeq: 재댓글을 작성하고자 하는 댓글의 키 (재댓글 작성 시에만 입력), content: 댓글
	 *         내용, visitIP: 방문자 PC의 IP Address (null = 모바일)
	 * @params_example targetId=&itemSeq=42391847&content=댓글&visitIp=127.0.0.1
	 * @references MINIHOME_GET_ALBUM_ITEMS
	 * @return XML String
	 */
	public static final String MINIHOME_CREATE_ALBUM_ITEM_REPLY = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RegisterPhotoReply/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 사진첩의 댓글을 삭제하는 API입니다. 본인이 작성한 사진첩 댓글 및 본인 미니홈피의 댓글 삭제가 가능합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq : 사진첩게시물키,
	 *         replySeq: 삭제 대상 댓글 키
	 * @params_example targetId=&itemSeq=42391847&replySeq=18450293
	 * @references MINIHOME_GET_ALBUM_ITEMS, MINIHOME_GET_ALUBM_ITEM_REPLYS
	 * @return XML String
	 */
	public static final String MINIHOME_REMOVE_ALBUM_ITEM_REPLY = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RemovePhotoReply/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 다이어리의 목록 정보를 불러오는 API입니다. 게시물의 공개 설정과 일촌 여부에 맞는 결과값을 보여줍니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), folderNo : 폴더 번호 (전체보기 :
	 *         0), cPage: 요청 페이지 번호, perPage: 요청 게시물 수 (범위:4~50), searchType:
	 *         <검색 타입 지정> 검색않음 : “0” 또는 “none” 내용검색 : “1” 또는 “content” 날짜검색 :
	 *         “2” 또는 “yyyyMMdd”, searchWord: 검색어, listType: <리스트 타입 지정> 전체폴더보기
	 *         : “1” 또는 “all” 개별폴더보기 : “2” 또는 “oneFolder”
	 * @params_example targetId=&folderNo=0&cPage=1&perPage=4
	 * @references MINIHOME_GET_ALBUM_ITEMS
	 * @return XML String
	 */
	public static final String MINIHOME_GET_DIARY_ITEMS = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RetrieveMyDiaryItemList/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 다이어리의 일별 게시물 정보를 불러오는 API입니다. 게시물의 공개 설정과 일촌 여부에 맞는 결과값을
	 *       보여줍니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), folderNo : 폴더 번호(전체보기:0 사용이
	 *         불가능합니다. 꼭 폴더번호를 지정하세요) yyyyMMdd: 다이어리 지정 날짜, listType: <리스트 타입
	 *         지정> 전체폴더보기 : “1” 또는 “all” 개별폴더보기 : “2” 또는 “oneFolder”
	 * @params_example targetId=&folderNo=4&yyyyMMdd=20121102
	 * @references MINIHOME_GET_FOLDERLIST
	 * @return XML String
	 */
	public static final String MINIHOME_GET_DIARY_ITEM_BYDATE = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RetrieveMyDiaryItemsByDate/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 다이어리에 새로운 게시물을 작성하는 API입니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), folderNo : 폴더 번호(전체보기:0 사용이
	 *         불가능합니다. 꼭 폴더번호를 지정하세요) yyyyMMdd: 다이어리 지정 날짜, content: 다이어리 내용
	 *         itemOpen: <게시물 공개 설정> 비공개 : “0” 또는 “secret” 일촌공개 : “1” 또는
	 *         “oneDegOpen” 전체공개 : “4” 또는 “allOpen”, face: 기분 (코드 표 참조),
	 *         weather: 날씨(코드 표 참조), photoUrl: 첨부 파일 URL(MINIHOME_UPLOAD_PHOTO 의
	 *         결과값) ‘face’ 코드 표 |face 코드|
	 *         |선택안함 0|그냥 6|황당 12|황홀 18|외로움 24| |설렘 1|바쁨 7|심심 13|뿌듯 19|답답 25|
	 *         |즐거움 2|피곤 8|열받음 14|개운 20|귀찮음 26| |짜증 3|아픔 9|사랑해 15|파이팅 21|그리움 27|
	 *         |슬픔 4|행복 10|앗싸 16|떨림 22|쓸쓸 28| |힘듦 5|우울 11|기쁨 17|불안 23| ‘weather’
	 *         코드 표 |weather 코드|선택안함 1|구름조금 3|비 5| |맑음
	 *         2|흐림 4|눈 6|
	 * @params_example targetId=&folderNo=4&yyyyMMdd=20121102&content=다이어리내용&itemOpen=oneDegOpen&weather=5&face=23
	 * @references MINIHOME_GET_FOLDERLIST, MINIHOME_UPLOAD_PHOTO
	 * @return <int xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{itemSeq}</int>
	 */
	public static final String MINIHOME_CREATE_DIARY_ITEM = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RegisterMyDiaryItem/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 다이어리 게시물의 공개설정(전체공개/일촌공개/비공개)을 변경하는 API입니다. 본인이 작성한 다이어리 게시물만
	 *       공개설정이 가능합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq : 다이어리 게시물 키,
	 *         itemOpen: <게시물 공개 설정> 비공개 : “0” 또는 “secret” 일촌공개 : “1” 또는
	 *         “oneDegOpen” 전체공개 : “4” 또는 “allOpen”
	 * @params_example targetId=&itemSeq=123456789&itemOpen=1
	 * @references MINIHOME_GET_DIARY_ITEMS
	 * @return <int
	 *         xmlns="http://schemas.microsoft.com/2003/10/Serialization/">1
	 *         </int>
	 */
	public static final String MINIHOME_MODIFY_DIARY_ITEM_OPENTYPE = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_ModifyMyDiaryItemOpen/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 다이어리 게시물을 다이어리 내 다른 폴더로 이동시키는 API입니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq : 다이어리 게시물 키,
	 *        tFolderNo: 이동할 폴더 번호.
	 * @params_example targetId=&itemSeq=123456789&tFolderNo=12
	 * @references MINIHOME_GET_DIARY_ITEMS, MINIHOME_GET_FOLDERLIST
	 * @return <int
	 *         xmlns="http://schemas.microsoft.com/2003/10/Serialization/">1
	 *         </int>
	 */
	public static final String MINIHOME_MOVE_DIARY_FOLDER = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_ModifyMyDiaryItemFolderMove/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 다이어리의 댓글 정보를 불러오는 API입니다. 게시물의 공개 설정과 일촌 여부에 맞는 결과값을 보여줍니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq : 다이어리 게시물 키,
	 *         cPage: 요청 댓글 페이지 번호, perPage: 페이지 당 요청 댓글 수 (범위:30~50)
	 * @params_example targetId=&itemSeq=123456789&cPage=1&perPage=30
	 * @references MINIHOME_GET_DIARY_ITEMS
	 * @return <ArrayOfMyDiaryReply xmlns=
	 *         "http://schemas.datacontract.org/2004/07/Cy.Service.OpenCy.Entity"
	 *         xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
	 *         <MyDiaryReply> <content>{content}</content>
	 *         <parentReplySeq>{pReqplySeq}</parentReplySeq>
	 *         <replySeq>{replySeq}</replySeq>
	 *         <totalReplyCount>{replyCount}</totalReplyCount> <writeDate>{writeDate}</writeDate> <writeDateDiff>{hoursAgo}</writeDateDiff>
	 *         <writerId>{cyId}</writerId> <writerName>{writerName}</writerName>
	 *         </MyDiaryReply> </ArrayOfMyDiaryReply>
	 */
	public static final String MINIHOME_GET_DIARY_ITEM_REPLIES = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RetrieveMyDiaryReply/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 다이어리 게시물 또는 댓글에 대하여 댓글 또는 재댓글을 작성합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq : 다이어리 게시물 키,
	 *         parentReplySeq: 재댓글을 작성하고자 하는 댓글의 키 (재댓글 작성 시에만 입력), content: 내용
	 * @params_example targetId=&itemSeq=123456789&content=다이어리댓글.
	 * @references MINIHOME_GET_DIARY_ITEMS, MINIHOME_GET_DIARY_ITEM_REPLIES
	 * @return <int
	 *         xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{
	 *         replySeq}</int>
	 */
	public static final String MINIHOME_CREATE_DIARY_ITEM_REPLY = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RegisterMyDiaryReply/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 다이어리의 댓글을 삭제하는 API입니다. 본인이 작성한 다이어리 댓글 및 본인 미니홈피의 댓글 삭제가 가능합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq : 다이어리 게시물 키,
	 *         replySeq: 삭제 대상 댓글 키
	 * @params_example targetId=&itemSeq=123456789&replySeq=388383183
	 * @references MINIHOME_GET_DIARY_ITEMS, MINIHOME_GET_DIARY_ITEM_REPLIES
	 * @return <int
	 *         xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{result_code}</int>
	 */
	public static final String MINIHOME_REMOVE_DIARY_ITEM_REPLY = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RemoveMyDiaryReply/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 방명록의 게시물 목록 정보를 불러오는 API입니다. 게시물의 공개 설정에 맞는 결과값을 보여줍니다. 요청과
	 *       관련된 추가 설명(규칙 등) 또는 주의 사항 1) 2008년 이전 방명록은 현재 조회가 불가능합니다. 2) 검색 기능은
	 *       본인의 미니홈피에서 이용 가능하며, 타인 미니홈피에서는 ‘내가 쓴 글 보기’기능만 사용 가능합니다. 3) ‘내가 쓴 글
	 *       보기’ 기능은 ‘searchType=2&searchWord=[로그인 회원의 targetId]’를 입력하여 구현할 수
	 *       있습니다. (동명 이인 문제로 이름으로는 구현이 불가능합니다)
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), listType : <리스트 타입 지정>
	 *         1:전체글보기, 2:공개글보기, 3:비밀글보기 2008년 이전 방명록 조회 불가, cPage: 요청 방명록 페이지
	 *         번호, perPage: 페이지 당 요청 게시물 수 (범위:5~50), searchYear: 대상 방명록 년도,
	 *         searchType: <검색 형태> 1:이름 검색, 2:아이디 검색, null:검색안함, searchWord: 검색
	 *         키워드 (검색하지 않을 경우 “null”)
	 * @params_example targetId=&listType=2&cPage=1&perPage=10
	 * @references MINIHOME_GET_HOMEINFO
	 * @return <ArrayOfVisitBook xmlns=
	 *         "http://schemas.datacontract.org/2004/07/Cy.Service.OpenCy.Entity"
	 *         xmlns:i="http://www.w3.org/2001/XMLSchema-instance"> <VisitBook>
	 *         <content>방명록 테스트</content>
	 *         <decoSrc>1,3,1680,1203,244,2644,2606,2798_2,767,F</decoSrc>
	 *         <decoType>1</decoType> <isSecret>true</isSecret>
	 *         <itemSeq>1085921221</itemSeq> <listNo>373</listNo>
	 *         <replyNo>0</replyNo> <seasonNo>0</seasonNo>
	 *         <totalCount>5</totalCount> <visitIP>61.106.160.17</visitIP>
	 *         <writeDate>2010-10-05 오후 9:15:00</writeDate>
	 *         <writerId>64617408</writerId> <writerName>미니홈피</writerName>
	 *         </VisitBook> </ArrayOfVisitBook>
	 */
	public static final String MINIHOME_GET_VISITBOOK_ITEMS = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RetrieveVisitBookList/v1";
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 방명록에 새로운 게시물을 작성하는 API입니다. 요청과 관련된 추가 설명(규칙 등) 또는 주의 사항 1) 일촌이
	 *       아닌 경우에는 비밀글을 작성할 수 없습니다. 2) isSecret 필드에 값을 입력하지 않을 경우, 기본값으로
	 *       ‘true(비밀 방명록)’가 적용됩니다. 이는 targetId가 일촌일 경우에 한정되며, targetId가 일촌이 아닌
	 *       경우에는 에러 메시지를 반환합니다. 3) visitIP 정보가 없을 경우에는 모바일로 인식하여, 미니홈피 댓글 이름UI
	 *       좌측에 모바일 아이콘이 노출됩니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), content : 방명록 게시물 내용
	 *         isSecret: <게시물 공개 여부> true : 비밀 방명록 false : 전체 공개 방명록, visitIP:
	 *         방문자 PC의 IP Address (null = 모바일)
	 * @params_example targetId=&content=방문하고갑니다&isSecret=false
	 * @references CYWORLD_CYS_GET_MYCYS
	 * @return <int
	 *         xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{
	 *         itemSeq}</int>
	 */
	public static final String MINIHOME_CREATE_VISITBOOK_ITEM = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RegisterVisitBookItem/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 방명록의 게시물을 비밀(비공개)로 변경하는 API입니다. 한 번 비밀로 설정한 글은 다시 공개할 수 없습니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), searchYear : 대상 방명록 년도 
* 2008년 이전 방명록 변경 불가, itemSeq: 방명록 게시물 키
	 * @params_example targetId=&searchYear=2010&itemSeq=113333222
	 * @references MINIHOME_GET_VISITBOOK_ITEMS
	 * @return <int
	 *         xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{
	 *         result_code}</int>
	 */
	public static final String MINIHOME_MODIFY_VISITBOOK_ITEM_OPENTYPE = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_ModifyVisitBookItemSecret/v1";
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 방명록의 게시물을 삭제하는 API입니다. 본인 미니홈피의 방명록의 게시글 삭제만 가능합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), searchYear : 대상 방명록 년도 
	 * 2008년 이전 방명록 삭제 불가, itemSeq: 방명록 게시물 키
	 * @params_example targetId=&searchYear=2010&itemSeq=11334444
	 * @references MINIHOME_GET_VISITBOOK_ITEMS
	 * @return <int
	 *         xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{
	 *         result_code}</int>
	 */
	public static final String MINIHOME_REMOVE_VISITBOOK_ITEM = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RemoveVisitBookItem/v1";
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 방명록 게시물에 대하여 댓글을 작성하는 API입니다. 본인 미니홈피 방명록에 한해서만 댓글 작성이 가능합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), listNo: 게시물 순서, searchYear: 게시물 작성 년도, content: 댓글 내용, visitIP: 방문자 PC의 IP Address (null = 모바일)
	 * @params_example targetId=&searchYear=2010&itemSeq=11334444
	 * @references MINIHOME_GET_VISITBOOK_ITEMS
	 * @return <int
	 *         xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{
	 *         result_code}</int>
	 */
	public static final String MINIHOME_CREATE_VISITBOOK_ITEM_REPLY = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RegisterVisitBookReply/v1";
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 방명록의 댓글을 삭제하는 API입니다. 본인 미니홈피 방명록의 댓글에 한해서만 삭제가 가능합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), searchYear: 대상 방명록 년도 2008년
	 *         이전 방명록 댓글 삭제 불가, itemSeq: 댓글 목록을 확인하고자 하는 방명록 게시물 키,
	 *         itemReplySeq: 목록 댓글 키
	 * @params_example 
	 *                 targetId=&searchYear=2010&itemSeq=11334444&itemReplySeq=55667788
	 * @references MINIHOME_GET_VISITBOOK_ITEMS
	 * @return <int
	 *         xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{
	 *         result_code}</int>
	 */
	public static final String MINIHOME_REMOVE_VISITBOOK_ITEM_REPLY = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RemoveVisitBookReply/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 게시판의 게시물 목록 정보를 불러오는 API입니다. 게시물의 공개 설정과 일촌 여부에 맞는 결과값을 보여줍니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), folderNo: 폴더 번호 (전체보기:0),
	 *         cPage: 요청 페이지 번호, searchType: <검색 타입 지정> 검색않음 : “0” 또는 “none”
	 *         제목검색 : “1” 또는 “title” 태그검색 : “2” 또는 “tag”, searchWord: 검색어
	 * @params_example targetId=&folderNo=0&cPage=1
	 * @references MINIHOME_GET_FOLDERLIST
	 * @return <ArrayOfGeneralBoardItemList xmlns=
	 *         "http://schemas.datacontract.org/2004/07/Cy.Service.OpenCy.Entity"
	 *         xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
	 *         <GeneralBoardItemList> <firstListNo>{firstListNo}</firstListNo>
	 *         <listIndent>{itemType}</listIndent> <listNo>{listNo}</listNo>
	 *         <listOrder>{listOrder}</listOrder> <visitCount>{visitCount}</visitCount>
	 *         <folderNo>{folderNo}</folderNo> <itemOpen>{itemOpenType}</itemOpen>
	 *         <itemSeq>{itemSeq}</itemSeq> <replyCount>{replyCount}</replyCount>
	 *         <title>{title}</title> <totalCount>{totalCount}</totalCount>
	 *         <writeDate>{writeDate}</writeDate> <writerId>{writerId}</writerId>
	 *         <writerName>{writerName}</writerName> </GeneralBoardItemList>
	 *         </ArrayOfGeneralBoardItemList>
	 */
	public static final String MINIHOME_GET_BOARD_ITEMS = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RetrieveBoardItemList/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 게시판의 게시물 상세 정보를 불러오는 API입니다. 게시물의 공개 설정과 일촌 여부에 맞는 결과값을 보여줍니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), folderNo: 폴더 번호 (전체보기:0),
	 *         itemSeq: 게시판 게시물 키
	 * @params_example targetId=&folderNo=0&itemSeq=11223344
	 * @references MINIHOME_GET_FOLDERLIST, MINIHOME_GET_BOARD_ITEMS
	 * @return <GeneralBoardItem xmlns=
	 *         "http://schemas.datacontract.org/2004/07/Cy.Service.OpenCy.Entity"
	 *         xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
	 *         <attachFileUrl> {attachFileUrl} </attachFileUrl> <content>
	 *         {content}</content> <scrapCount>{scrapCount}</scrapCount>
	 *         <visitCount>{visitCount}</visitCount>
	 *         <folderNo>{folderNo}</folderNo>
	 *         <itemOpen>{itemOpenType}</itemOpen> <itemSeq>{itemSeq}</itemSeq>
	 *         <replyCount>{replyCount}</replyCount> <title>{title}</title>
	 *         <writeDate>{writeDate}</writeDate>
	 *         <writerId>{writerId}</writerId>
	 *         <writerName>{writerName}</writerName> </GeneralBoardItem>
	 */
	public static final String MINIHOME_GET_BOARD_ITEM = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RetrieveBoardItem/v1";
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 게시판에 새로운 게시물을 작성하는 API입니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), folderNo: 폴더 번호 (전체보기:0),
	 *         title: 제목, content: 게시판 내용, itemOpen: <게시물 공개 설정> 비공개 : “0” 또는
	 *         “secret” 일촌공개 : “1” 또는 “oneDegOpen” 전체공개 : “4” 또는 “allOpen”,
	 *         photoUrl: 첨부 파일 URL, isScrapOpen: 스크랩 허용여부, isSearchOpen: 검색 허용여부
	 * @params_example targetId=&folderNo=0&title=제목&content=게시판내용&itemOpen=0
	 * @references MINIHOME_GET_FOLDERLIST, MINIHOME_UPLOAD_PHOTO
	 * @return <int
	 *         xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{
	 *         itemSeq}</int>
	 */
	public static final String MINIHOME_CREATE_BOARD_ITEM = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RegisterBoardItem/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 게시판의 게시물 제목을 수정하는 API입니다. 본인이 작성한 게시판 게시물만 제목 수정이 가능합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq: 게시물 키, title: 수정할 제목
	 * @params_example targetId=&itemSeq=11112222&title=수정할제목
	 * @references MINIHOME_GET_BOARD_ITEMS
	 * @return <int
	 *         xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{
	 *         result_code}</int>
	 */
	public static final String MINIHOME_MODIFY_BOARD_ITEM_TITLE = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_ModifyBoardItemTitle/v1";
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 게시판 게시물의 공개설정(전체공개/일촌공개/비공개)을 변경하는 API입니다. 본인이 작성한 게시판 게시물만
	 *       공개설정 변경이 가능합니다. 공개설정 변경은 ‘전체공개->일촌공개->비공개’로만 가능합니다. 즉, 비공개 게시물을
	 *       일촌공개로 변경하는 것은 공개설정 수정하기 API에서 지원하지 않습니다. 이는 사용자의 게시물이 의도하지 않게 타인에게
	 *       보여지도록 수정되는 것을 방지하기 위함입니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq: 게시물 키, itemOpen:
	 *         <게시물 공개 설정> 비공개 : “0” 또는 “secret” 일촌공개 : “1” 또는 “oneDegOpen” 전체공개
	 *         : “4” 또는 “allOpen”
	 * @params_example targetId=&itemSeq=11112222&itemOpen=0
	 * @references MINIHOME_GET_BOARD_ITEMS
	 * @return <int
	 *         xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{
	 *         result_code}</int>
	 */
	public static final String MINIHOME_MODIFY_BOARD_ITEM_OPENTYPE = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_ModifyBoardItemOpen/v1";
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 게시판의 게시물을 게시판 내 다른 폴더로 이동시키는 API입니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq: 게시물 키, tFolderNo: 이동할 폴더 번호
	 * @params_example targetId=&itemSeq=11112222&itemOpen=0
	 * @references MINIHOME_GET_BOARD_ITEMS, MINIHOME_GET_FOLDERLIST
	 * @return <int
	 *         xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{
	 *         result_code}</int>
	 */
	public static final String MINIHOME_MOVE_BOARD_FOLDER = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_ModifyBoardItemFolderMove/v1";
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 게시판 게시물의 댓글 목록 정보를 불러오는 API입니다. 게시물의 공개 설정과 일촌 여부에 맞는 결과값을
	 *       보여줍니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq: 게시물 키, cPage: 요청
	 *         댓글 페이지 번호, perPage: 페이지 당 요청 댓글 수(범위:10~50)
	 * @params_example targetId=&itemSeq=11112222&cPage=1&perPage=15
	 * @references MINIHOME_GET_BOARD_ITEMS
	 * @return <ArrayOfPhotoReply xmlns=
	 *         "http://schemas.datacontract.org/2004/07/Cy.Service.OpenCy.Entity"
	 *         xmlns:i="http://www.w3.org/2001/XMLSchema-instance"> <PhotoReply>
	 *         <content>{content}</content> <itemSeq>{itemSeq}</itemSeq>
	 *         <parentReplySeq>{parentReplySeq}</parentReplySeq>
	 *         <replySeq>{replySeq}</replySeq>
	 *         <totalReplyCount>{totalCount}</totalReplyCount>
	 *         <writeDate>{writeDate}</writeDate>
	 *         <writerId>{writerId}</writerId>
	 *         <writerName>{writerName}</writerName> </PhotoReply>
	 *         </ArrayOfPhotoReply>
	 */
	public static final String MINIHOME_GET_BOARD_ITEM_REPLIES = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RetrieveBoardReplyList/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 게시판 게시물 또는 댓글에 대하여 댓글 또는 재댓글을 작성하는 API입니다. 댓글 허용 설정 및 일촌 여부에 따라 댓글 작성 가능 여부가 결정됩니다.
	 *       
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq: 게시물 키, parentReplySeq: 재댓글을 작성하고자 하는 댓글의 키(재댓글 작성시만 입력), content: 댓글내용 
	 * @params_example targetId=&itemSeq=11112222&content=댓글댓글 
	 * @references MINIHOME_GET_BOARD_ITEMS, MINIHOME_GET_BOARD_ITEM_REPLIES
	 * @return <int xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{replySeq}</int>
	 */
	public static final String MINIHOME_CREATE_BOARD_ITEM_REPLY = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RegisterBoardReply/v1";
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 게시판의 댓글을 삭제하는 API입니다. 본인이 작성한 게시판 댓글 및 본인 미니홈피의 댓글 삭제가 가능합니다.
	 *       
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq: 게시물 키, replySeq: 삭제대상 댓글번호  
	 * @params_example targetId=&itemSeq=11112222&replySeq=111338483482
	 * @references MINIHOME_GET_BOARD_ITEMS, MINIHOME_GET_BOARD_ITEM_REPLIES
	 * @return <int xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{result_code}</int>
	 */
	public static final String MINIHOME_REMOVE_BOARD_ITEM_REPLY = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RemoveBoardReply/v1";
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 상단의 이름(타이틀)을 보여줍니다.
	 *       
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보)  
	 * @params_example targetId=12345678
	 * @references CYWORLD_CYS_GET_MYCYS
	 * @return <string xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{MINIHOME's title}</string>
	 */
	public static final String MINIHOME_GET_HOME_TITLE = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/RetrieveMinihpTitle/v1";

	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피의 TODAY, TOTAL 방문자수 통계를 보여줍니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보)  
	 * @params_example targetId=12345678
	 * @references CYWORLD_CYS_GET_MYCYS
	 * @return <string xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{Today},{Total}</string>
	 */
	public static final String MINIHOME_GET_HOME_VISITCOUNT = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/RetrieveMinihpVisitCount/v1";
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피의 사진첩, 다이어리, 게시판, 방명록의 게시물 업데이트 정보를 보여줍니다. 메뉴의 오픈 여부에 따라 전체 게시물
	 *       수, 업데이트 게시물 수를 보여줍니다. 홈의 ‘최근 게시물/업데이트 메뉴’를 사용하지 않을 경우 조회가 불가합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보)
	 * @params_example targetId=12345678
	 * @references CYWORLD_CYS_GET_MYCYS
	 * @return <UpdatedContentsCount xmlns=
	 *         "http://schemas.datacontract.org/2004/07/Cy.Service.OpenCy.Entity"
	 *         xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
	 *         <bbsTotalCount>{boardTotalCount}</bbsTotalCount>
	 *         <bbsUpdateCount>{boardUpdatedCount}</bbsUpdateCount>
	 *         <diaryTotalCount>{diaryTotalCount}</diaryTotalCount>
	 *         <diaryUpdateCount>{diaryUpdatedCount}</diaryUpdateCount>
	 *         <photoTotalCount>{albumTotalCount}</photoTotalCount>
	 *         <photoUpdateCount>{albumUpdatedCount}</photoUpdateCount>
	 *         <visitNoteTotalCount>{vistbookTotalCount}</visitNoteTotalCount>
	 *         <visitNoteUpdateCount
	 *         >{visitbookUpdatedCount}</visitNoteUpdateCount>
	 *         </UpdatedContentsCount>
	 */
	public static final String MINIHOME_GET_HOME_UPDATED_CONTENTS = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/RetrieveUpdatedContents/v1";
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피의 포토스토리 콘텐츠 정보를 보여줍니다. 포토스토리는 미니홈피 주인 본인이 직접 꾸며놓은 메뉴로 폴더 및 게시물의
	 *       공개설정에 관계없이 모두 보기가 가능합니다. 홈의 ‘포토스토리’를 사용하지 않을 경우 조회가 불가합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보)
	 * @params_example targetId=12345678
	 * @references CYWORLD_CYS_GET_MYCYS
	 * @return <ArrayOfPhotoFrame xmlns=
	 *         "http://schemas.datacontract.org/2004/07/Cy.Service.OpenCy.Entity"
	 *         xmlns:i="http://www.w3.org/2001/XMLSchema-instance"> <PhotoFrame>
	 *         <boardType>{boardType}</boardType>
	 *         <frameSet>{frameSetNo}</frameSet>
	 *         <imageNo>{imageOrderNo}</imageNo>
	 *         <imageTitle>{imageTitle}</imageTitle>
	 *         <imageUrl>{imaegUrl}</imageUrl> <itemSeq>{itemSeq}</itemSeq>
	 *         </PhotoFrame> </ArrayOfPhotoFrame>
	 */
	public static final String MINIHOME_GET_HOME_PHOTOSTORY = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/RetrievePhotoFrame/v1";
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 일촌평의 목록 정보를 불러오는 API 입니다. 홈의 ‘일촌평’ 메뉴를 사용하지 않을 경우 조회가 불가합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), page: 요청페이지번호(페이지당 10개 출력)
	 * @params_example targetId=12345678&page=2
	 * @references CYWORLD_CYS_GET_MYCYS
	 * @return <ArrayOfOneDegNote xmlns=
	 *         "http://schemas.datacontract.org/2004/07/Cy.Service.OpenCy.Entity"
	 *         xmlns:i="http://www.w3.org/2001/XMLSchema-instance"> <OneDegNote>
	 *         <Comment>{comment}</Comment>
	 *         <CommentReplyCount>{replyCount}</CommentReplyCount>
	 *         <Did>{writerId}</Did> <Didname>{writerName}</Didname>
	 * 
	 *         <TotalCount>{commentTotalCount}</TotalCount>
	 *         <UpdatedTime>{updatedTime}</UpdatedTime> </OneDegNote>
	 *         </ArrayOfOneDegNote>
	 */
	public static final String MINIHOME_GET_HOME_CYS_COMMENT = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/RetrieveOneDegNoteList/v1";	
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 일촌평에 새로운 글을 작성하는 API 입니다. 홈의 ‘일촌평’ 메뉴를 사용하지 않을 경우 작성하기가 불가합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), comment: 일촌평 내용 
	 * @params_example targetId=12345678&comment=일촌안녕 
	 * @references CYWORLD_CYS_GET_MYCYS
	 * @return <int xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{result_code}</int>
	 */
	public static final String MINIHOME_CREATE_HOME_CYS_COMMENT = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/RegisterOneDegNote/v1";	
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 일촌평을 삭제하는 API 입니다. 본인 미니홈피의 일촌평 및 타인 미니홈피에 본인이 작성한 일촌평 삭제가 가능합니다. 홈의 ‘일촌평’ 메뉴를 사용하지 않을 경우 삭제가 불가합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), did: 삭제하고자 하는 일촌평 작성자의 아이디 
	 * @params_example targetId=12345678&did=22223333
	 * @references CYWORLD_CYS_GET_MYCYS
	 * @return <int xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{result_code}</int>
	 */
	public static final String MINIHOME_REMOVE_HOME_CYS_COMMENT = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/RemoveOneDegNote/v1";	
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 일촌평의 댓글 목록 정보를 불러오는 API 입니다. 홈의 ‘일촌평’ 메뉴를 사용하지 않을 경우 조회가
	 *       불가합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), did: 작성자의 아이디, page:
	 *         요청페이지번호(페이지당 10개 출력)
	 * @params_example targetId=12345678&did=22223333&page=1
	 * @references CYWORLD_CYS_GET_MYCYS
	 * @return <ArrayOfOneDegNote xmlns=
	 *         "http://schemas.datacontract.org/2004/07/Cy.Service.OpenCy.Entity"
	 *         xmlns:i="http://www.w3.org/2001/XMLSchema-instance"> <OneDegNote>
	 *         <Comment>{comment}</Comment> <Did>{writerId}</Did>
	 *         <Didname>{writerName}</Didname> <ItemSeq>{itemSeq}</ItemSeq>
	 *         <UpdatedTime>{updatedTime}</UpdatedTime> </OneDegNote>
	 *         </ArrayOfOneDegNote>
	 */
	public static final String MINIHOME_GET_HOME_CYS_COMMENT_REPLIES = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/RetrieveOneDegNoteCommentList/v1";	
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 일촌평에 댓글을 작성하는 API 입니다. 홈의 ‘일촌평’ 메뉴를 사용하지 않을 경우 작성하기가 불가합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), did: 작성자의 아이디, comment: 일촌평 댓글 내용 
	 * @params_example targetId=12345678&did=222233333&comment=일촌평댓글내용 
	 * @references CYWORLD_CYS_GET_MYCYS
	 * @return <int xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{replySeq}</int>
	 */
	public static final String MINIHOME_CREATE_HOME_CYS_COMMENT_REPLIY = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/RegisterOneDegNoteComment/v1";	
	
	/**
	 * @service MINIHOME(미니홈피)
	 * @desc 미니홈피 일촌평 댓글을 삭제하는 API 입니다. 본인 미니홈피의 일촌평 댓글 및 타인 미니홈피에 본인이 작성한 일촌평 댓글 삭제가 가능합니다. 홈의 ‘일촌평’ 메뉴를 사용하지 않을 경우 삭제가 불가합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), itemSeq: 삭제하고자 하는 일촌평 댓글 키 
	 * @params_example targetId=12345678&itemSeq=22333888 
	 * @references CYWORLD_CYS_GET_MYCYS, MINIHOME_GET_HOME_CYS_COMMENT_REPLIES
	 * @return <int xmlns="http://schemas.microsoft.com/2003/10/Serialization/">{result_code}</int>
	 */
	public static final String MINIHOME_REMOVE_HOME_CYS_COMMENT_REPLIY = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/RemoveOneDegNoteComment/v1";	

	/**
	 * @service CYS(일촌)
	 * @desc 본인의 관심일촌 목록을 불러오는 API입니다. 결과값으로 본인이 등록한 관심일촌의 TID값을 반환합니다.
	 * @params
	 * @params_example
	 * @references
	 * @return <ArrayOfstring
	 *         xmlns="http://schemas.microsoft.com/2003/10/Serialization/Arrays"
	 *         xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
	 *         <string>{targetId}</string> <string>{targetId}</string>
	 *         </ArrayOfstring>
	 */
	public static final String CYWORLD_CYS_GET_MYCYS = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RetrieveOnedegListIds/v1";

	/**
	 * @service CYS(일촌)
	 * @desc 본인의 관심일촌 목록 및 정보를 불러오는 API입니다. 결과값으로 본인이 등록한 관심일촌의 TID값과 함께 이름,
	 *       일촌명, 그룹 번호를 반환합니다.
	 * @params
	 * @params_example
	 * @references
	 * @return <ArrayOfOneDegEntity
	 *         xmlns="http://schemas.datacontract.org/2004/07/Cy.Entity.OneDeg"
	 *         xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
	 *         <OneDegEntity> <did>{targetId}</did> <didname>{name}</didname>
	 *         <relationname>{cysName}</relationname>
	 *         <groupNo>{groupNo}</groupNo>
	 *         </OneDegEntity></ArrayOfOneDegEntity>
	 */
	public static final String CYWORLD_CYS_GET_MYCYS_DETAIL = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RetrieveOnedegList/v1";
	
	/**
	 * @service CYS(일촌)
	 * @desc 싸이월드 회원의 프로필을 조회하는 API입니다. 프로필 조회하기는 일촌 관계가 아니라도 조회가 가능하며, 세부 정보는
	 *       대상 회원의 공개설정에 맞게 노출됩니다. 1) fieldNames 필드에서 age, birthday
	 *       항목은 싸이월드 개인정보 공개설정 여부에 따라 노출 여부가 결정됩니다. 2) aboutMe 필드는
	 *       UTF8 값을 Base64로 인코딩된 값을 반환합니다. 따라서 값을 화면상에 정상적으로 표현하기 위해서는 Base64로
	 *       디코딩하여 사용하시기 바랍니다. 3) address는 개인정보 이슈로 인해 노출되지 않습니다.
	 * @params targetIds: 대상싸이월드 아이디(null로 세팅시 본인정보), fieldNames: <프로필 요청 항목>
	 *         name : 이름 aboutMe : 미니홈피 대문글 age : 나이(한국식) birthday : 생년월일 gender
	 *         : 성별 interests : 심볼이름 목록 (최대 3개) thumbnailUrl : 미니홈피 대문사진 all :
	 *         모든 항목 (“,” 분리자를 통해 복수 요청 가능), profileThumb: <프로필 이미지 썸네일 사이즈>
	 *         1=45x45 2=65x65 3=80x80 4=150x150
	 * @params_example targetIds=11112222,33334544&fieldNames=all&profileThumb=1
	 * @references CYWORLD_CYS_GET_MYCYS
	 * @return <ArrayOfCyProfile xmlns=
	 *         "http://schemas.datacontract.org/2004/07/Cy.Service.Member.Entity"
	 *         xmlns:i="http://www.w3.org/2001/XMLSchema-instance"> <CyProfile>
	 *         <aboutMe>{minihome's title}</aboutMe> <age>{age}</age>
	 *         <birthday>{birthday}</birthday> <gender>{gender}</gender>
	 *         <interests xmlns:a=
	 *         "http://schemas.microsoft.com/2003/10/Serialization/Arrays">
	 *         <a:string>{symbolName}</a:string> </interests>
	 *         <name>{name}</name> <thumbnailUrl>{profileUrl}</thumbnailUrl>
	 *         <userId>{targetId}</userId> </CyProfile> </ArrayOfCyProfile>
	 */
	public static final String CYWORLD_CYS_GET_PROFILE = "https://openapi.nate.com/OApi/RestApiSSL/CY/200110/xml_RetrieveMemProfile/v1";
	
	/**
	 * @service NOTE(노트)
	 * @desc 노트 글 목록 정보를 불러오는 API입니다. 게시물의 공개 설정에 맞는 결과값을 보여줍니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), cPage: 요청페이지번호, pPage: 페이지당
	 *         요청게시물 수 (범위) - pPage 는 1~50 범위 내에서 사용 가능 하고, 1보다 작으면 기본 값인 5로
	 *         50보다 크면 50으로 설정됩니다, profileThumb: <프로필 이미지 썸네일 사이즈> 0 : 원본 1 :
	 *         45x45 2 : 65x65 3 : 80x80 (default) 4 : 150x150 , contentThumb:
	 *         <컨텐츠 이미지 썸네일 사이즈> 0 : 원본 1 : 108x108 3 : 200x200 , abstract: <게시
	 *         글 길이 또는 원본 표현 형식> 0: 원본 기본 100자, 최대 500자, filter: <글 보기 타입> 0 :
	 *         전체보기 1 : 홈 주인이 쓴 글 2 : 일촌,팬이 쓴 글(홈 주인이 볼 때) or 자기가 쓴 글(일촌,팬이 볼 때)
	 *         , 방문자사용불가 9 : 노트 주인이 쓴 공개 글, output: xml/json
	 * @params_example 
	 *                 targetId=&abstract=30&cPage=1&contentThumb=3&filter=0&output
	 *                 =json&pPage=2&profileThumb=3
	 * @references CYWORLD_CYS_GET_MYCYS
	 * @return_example 
	 *         {"id":"12345678","totalCount":11,"item":[{"noteSeq":100,"deviceType"
	 *         :"0","openType":"4","relationType":"0","commentCount":0,
	 *         "writeDate":"1292574420","writerId":"12345678","writerName":
	 *         "\ud64d\uae38\ub3d9","writerHomeUrl":
	 *         "http:\/\/cyhome.cyworld.com\/?home_id=a0000000","profileUrl":"http://cyimg31.cyworld.com/...../abc.jpg","imgUrl":"
	 *         http://cyimg31.cyworld.com/...../abc.jpg
	 *         ","externalUrl":"","contents
	 *         ":"\uc548\ub155\ud558\uc138\uc694","imageSize
	 *         ":""},{"noteSeq":101,"
	 *         deviceType":"0","openType":"4","relationType
	 *         ":"0","commentCount":0,"
	 *         writeDate":"1292574240","writerId":"12345678
	 *         ","writerName":"\ud64d\uae38\ub3d9
	 *         ","writerHomeUrl":"http:\/\/cyhome
	 *         .cyworld.com\/?home_id=a0000000","
	 *         profileUrl":"http://cyimg31.cyworld.com/...../abc.jpg","imgUrl":"
	 *         http://cyimg31.cyworld.com/...../abc.jpg
	 *         ","externalUrl":"","contents
	 *         ":"\ubc18\uac11\uc2b5\ub2c8\ub2e4","imageSize
	 *         ":"80x80"}],"rcode":"RET0000","rmsg":"success"}
	 */
	public static final String CYWORLD_NOTE_GET_ITMES = "https://openapi.nate.com/OApi/RestApiSSL/CY/200800/getnotelist/v1";
	
	/**
	 * @service NOTE(노트)
	 * @desc 특정 노트의 상세 보기 API 입니다. 비 로그인 조회 가능합니다.
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), noteSeq: 글번호, profileThumb:
	 *         <프로필 이미지 썸네일 사이즈> 0 : 원본 1 : 45x45 2 : 65x65 3 : 80x80 (default)
	 *         4 : 150x150 , contentThumb: <컨텐츠 이미지 썸네일 사이즈> 0 : 원본 1 : 108x108
	 *         3 : 200x200 , abstract: <게시 글 길이 또는 원본 표현 형식> 0: 원본 기본 100자, 최대
	 *         500자, output: xml/json
	 * @params_example 
	 *                 targetId=12345678&noteSeq=11122222&abstract=30&contentThumb
	 *                 =3&output=json&profileThumb=3
	 * 
	 * @references CYWORLD_CYS_GET_MYCYS, CYWORLD_NOTE_GET_NOTES
	 * @return_example 
	 *                 {"item":{"id":"12345678","noteSeq":100,"deviceType":"0","openType"
	 *                 :"4","relationType":"0","commentCount":0,"writeDate":
	 *                 "1292574420"
	 *                 ,"writerId":"12345678","writerName":"\ud64d\uae38\ub3d9"
	 *                 ,"writerHomeUrl":
	 *                 "http:\/\/cyhome.cyworld.com\/?home_id=a0000000","profileUrl":"http:\/\/cyimg31.cyworld.com\/...\/abc.jpg","imgUrl":"http:\/\/cyimg31.cyworld.com\/...\/abc.jpg","contents":"\uc548\ub155\ud558\uc138\uc694
	 *                 ","
	 *                 externalUrl":"","photoSize":"200x200"},"rcode":"RET0000
	 *                 ","rmsg":"success"}
	 */
	public static final String CYWORLD_NOTE_GET_ITEM_DETAIL = "https://openapi.nate.com/OApi/RestApiSSL/CY/200800/getnote/v1";	
	
	/**
	 * @service NOTE(노트)
	 * @desc 노트 작성 하기
	 * @params targetId: 대상싸이월드 아이디(null로 세팅시 본인정보), openType: < 공개 설정 > 0 :
	 *         주인에게만 공개 1 : 일촌공개 4 : 전체공개, attachType: 1(사진), contents: 글 내용,
	 *         sendto: 0 : 연동 서비스에 보내지 않음 1 : 트위터 전송, attachId: 첨부파일 정보,
	 *         deviceType: < 장치정보 > 0 : web 1 : mobile, output: xml/json
	 * @params_example 
	 *                 targetId=12345678&attachId=&attachType=&contents=%EC%95%88
	 *                 %EB%85%95%ED%95%98%EC%84%B8%EC%9A%94.&openType=0&output=
	 *                 json&sendto=1
	 * @references CYWORLD_CYS_GET_MYCYS
	 * @return_example {"noteSeq":"102","rcode":"RET0000","rmsg":"success"}
	 * 
	 */
	public static final String CYWORLD_NOTE_CREATE_ITEM = "https://openapi.nate.com/OApi/RestApiSSL/CY/200800/addnote/v1";	
	
	public static final String NATESEARCH_SEARCH_HOTISSUE = "https://openapi.nate.com/OApi/RestApiSSL/NC/300010/searchHotIssue/v1";
	
	public static final String NATESEARCH_AUTOCOMPLETE = "https://openapi.nate.com/OApi/RestApiSSL/NC/300010/autoComplete/v1";

	
	
	/**
	 * @service NateON(네이트온)
	 * @desc 네이트온 친구목록을 조회합니다.친구 목록 조회 API를 통해 사용자의 친구 목록의 이름과 ID를 조회할 수 있습니다. 
	 * 조회할 친구 목록 개수와 정렬 방식을 지정할 수 있으며, 지정하지 않을 경우 ID순으로 정렬되어 상위 10개의 친구 목록이 조회됩니다. 
	 * 조회할 수 있는 최대 친구 목록 개수는 100개이며, 이름과 ID를 기준으로 정렬이 가능합니다. 
 	 * @params range: 조회하고자 하는 친구목록의 시작/끝 인덱스 번호(범위값이 100을 초과할 수 없음), order: 리스트의 정렬방식(id.asc, id.desc, name.asc, name.desc)
 	 * @params_example : range=0,10&order=id.asc
	 * @return XML String
	 */
	public static final String NATEON_GET_BUDDIES = "https://openapi.nate.com/OApi/RestApiSSL/ON/250020/nateon_GetBuddyList/v1";
	
	/**
	 * @service NateOn(네이트온)
	 * @desc 네이트온 친구목록 정보를 조회합니다.친구 목록 조회 API를 통해 사용자의 친구 목록의 이름과 ID, 프로필이미지 를 조회할 수 있습니다 
	 * 조회할 친구 목록 개수와 정렬 방식을 지정할 수 있으며, 지정하지 않을 경우 ID순으로 정렬되어 상위 10개의 친구 목록이 조회됩니다. 
	 * 조회할 수 있는 최대 친구 목록 개수는 100개이며, 이름과 ID를 기준으로 정렬이 가능합니다. 
 	 * @params range: 조회하고자 하는 친구목록의 시작/끝 인덱스 번호(범위값이 100을 초과할 수 없음), order: 리스트의 정렬방식(id.asc, id.desc, name.asc, name.desc)	 
 	 * @params_example : range=0,10&order=id.asc
	 * @return XML String
	 */
	public static final String NATEON_GET_BUDDYINFOS = "https://openapi.nate.com/OApi/RestApiSSL/ON/250020/nateon_GetBuddyInfo/v1";
	
	/**
	 * @service NateOn(네이트온)
	 * @desc 네이트온 친구에게 쪽지를 발송합니다.쪽지 발송 API를 통해 다른 사용자에게 네이트온 쪽지를 발송할 수 있습니다.
	 *       수신자를 지정하여 2000자 이내의 메시지로 발송을 해야하며, 수신자는 최대 50명까지 지정할 수 있습니다. 쪽지가
	 *       발송되지 않는 경우는 아래와 같습니다.1. 등록된 스패머이거나, 스팸 패턴에 해당되는 쪽지 내용 또는 IP일 때 (응답
	 *       : 정상)2. 친구에게만 쪽지 받기 옵션이 설정된 비 버디에게 발송할 때, (응답 : 정상)3. 발신자가 직권 정지
	 *       상태일 때 (응답 : 오류)4. 본인에게 쪽지를 발송할 때 (응답 : 오류)5. 수신자중에 중복된 id가 존재할 때
	 *       (응답 : 오류)6. 수신자수가 50명이 넘었을 때 (응답 : 오류)7. 발신자의 계정이 존재하지 않을 때 (응답 :
	 *       오류)
	 * @params ref: 수신자들의 ID 목록으로 각각의 ID는 세미콜론(;) 으로 구분하며, 아래와 같은 형식으로 요청하시길
	 *         권유합니다, body: 전송할 쪽지 내용으로 2000자를 초과할 수 없습니다, confirm: 수신확인 여부
	 *         (Y:수신확인 함, N:수신확인 안함)
	 * @params_example : ref=abc@nate.com&body=HelloBuddy
	 * @return XML String
	 */
	public static final String NATEON_SEND_NOTE = "https://openapi.nate.com/OApi/RestApiSSL/ON/250060/nateon_SendNote/v1";
	
	

}
