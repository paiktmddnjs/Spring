<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>게시글 작성</title>

<style>
.board-container {
	max-width: 800px;
	margin: 50px auto;
	padding: 2rem;
}

.board-card {
	background: white;
	border-radius: 8px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
	padding: 2rem;
}

.board-card h2 {
	text-align: center;
	color: #333;
	margin-bottom: 2rem;
	padding-bottom: 1rem;
	border-bottom: 2px solid #4b89fc;
}

.form-table {
	width: 100%;
	margin-bottom: 2rem;
}

.form-table tr {
	border-bottom: 1px solid #f0f0f0;
}

.form-table th {
	width: 120px;
	padding: 1rem;
	font-weight: 500;
	color: #555;
	text-align: left;
	vertical-align: top;
}

.form-table td {
	padding: 1rem;
}

.form-table select, .form-table input[type="text"], .form-table textarea,
	.form-table input[type="file"] {
	width: 100%;
	padding: 0.5rem;
	border: 1px solid #ddd;
	border-radius: 4px;
	font-size: 0.95rem;
	font-family: "Noto Sans KR", sans-serif;
}

.form-table textarea {
	resize: none;
}

.button-group {
	display: flex;
	justify-content: center;
	gap: 0.5rem;
	margin-top: 2rem;
}
</style>
</head>
<c:if test="${not empty alertMsg}">
    <script>
        alert("${alertMsg}");
    </script>
</c:if>
<body>
	<jsp:include page="/WEB-INF/views/common/menubar.jsp" />

	<div class="board-container">
		<div class="board-card">
			<h2>일반게시글 작성하기</h2>

			<form action="${pageContext.request.contextPath}/insert.bo" method="post" enctype="multipart/form-data">
				<table class="form-table">
					<tr>
						<th>카테고리</th>
						<td><select name="category">
								<option value="10">공통</option>
								<option value="20">운동</option>
								<option value="30">등산</option>
								<option value="40">게임</option>
								<option value="50">낚시</option>
								<option value="60">요리</option>
								<option value="70">기타</option>
						</select></td>
					</tr>
					<tr>
						<th>제목</th>
						<td><input type="text" name="title" required></td>
					</tr>
					<tr>
						<th>내용</th>
						<td><textarea name="content" rows="10"></textarea></td>
					</tr>
					<tr>
						<th>첨부파일</th>
						<td><input type="file" name="upfile"></td>
					</tr>
				</table>

				<div class="button-group">
					<button type="submit" class="btn btn-primary">작성하기</button>
					<button type="reset" class="btn btn-secondary">취소하기</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>