<style>
body {
	max-width: 620px;
	margin: 40px auto;
	background: #f9f9f9;
	color: #333;
	padding: 20px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	border-radius: 8px;
}

h1 {
	text-align: center;
}

h1, h2 {
	color: #2c3e50;
	border-bottom: 2px solid #3498db;
	padding-bottom: 6px;
}

a {
	color: #2980b9;
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}

form {
	margin-top: 20px;
}

label {
	font-weight: 600;
}

input[type="text"], textarea {
	width: 100%;
	max-width: 600px;
	padding: 10px;
	margin-top: 6px;
	margin-bottom: 15px;
	border: 1px solid #ccc;
	border-radius: 4px;
	font-size: 1em;
}

input[type="submit"], button {
	background-color: #3498db;
	color: white;
	padding: 10px 25px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 1em;
}

input[type="submit"]:hover, button:hover {
	background-color: #2980b9;
}

p.error {
	color: #c0392b;
	font-weight: bold;
}

.status-verified {
	color: green;
	font-weight: bold;
}

.status-unverified {
	color: red;
	font-weight: bold;
}

/* 표 스타일 */
table {
	border-collapse: collapse;
	width: 100%;
	margin-top: 20px;
	background: white;
	border-radius: 6px;
	overflow: hidden;
}

th, td {
	border: 1px solid #ddd;
	padding: 12px 15px;
	text-align: left;
}

th {
	background-color: #3498db;
	color: white;
}

ul {
	list-style-type: none;
	padding-left: 0;
}

ul li {
	background-color: white;
	margin-bottom: 12px;
	padding: 12px 20px;
	border-radius: 6px;
	box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
	transition: background-color 0.3s ease;
}

ul li a {
	color: #2c3e50;
	text-decoration: none;
}

ul li:hover {
	background-color: #2980b9;
}

ul li:hover a {
	color: white;
	text-decoration: none;
}
</style>
