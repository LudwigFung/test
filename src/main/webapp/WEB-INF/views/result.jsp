<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<body>
<h2></h2>

<span style="margin-left: 10px">

    总共记录 ：${total} 条；共 ${totalPages} 页；第 ${queryVo.page}  页；
    <a href='javascript:void(0)' onclick="previousPage();" style="text-decoration: none">[上一页]</a>
    <a href='javascript:void(0)' onclick="nextPage();" style="text-decoration: none">[下一页]</a>
    <a href='/home' style="text-decoration: none">[返回]</a>
</span>
<table border="1">
    <tr>
        <th>日志时间</th>
        <th>线程名</th>
        <th>日志级别</th>
        <th>类名</th>
        <th>日志详情</th>
    </tr>
    <c:forEach items="${appLogs}" var="log">
        <tr>
            <td style="width: 7%"><fmt:formatDate value="${log.logTime}" pattern="yyyy-MM-dd HH:mm:ss,SSS"/></td>
            <td style="width: 9%">${log.threadName}</td>
            <td style="width: 5%">${log.logLevel}</td>
            <td style="width: 20%">${log.className}</td>
            <td>${log.logInfo}</td>
        </tr>
    </c:forEach>

</table>


<form id="search" name="search" action="/log/queryLog" method="post">
    <input type="hidden" name="timeStart" value="${queryVo.timeStart}"><br>
    <input type="hidden" name="timeEnd" value="${queryVo.timeEnd}"><br>
    <input type="hidden" name="className" value="${queryVo.className}"><br>
    <input type="hidden" name="level" value="${queryVo.level}"><br>
    <input type="hidden" name="info" value="${queryVo.logInfo}"><br>
    <input id="page" type="hidden" name="page" ><br>
</form>

<script>
    var previousPage = function(){
        var page = "${queryVo.page}";
        if (page !== "1") {
            document.getElementById("page").value = --page;
            document.search.submit();
        }
    };

    var nextPage = function () {
        var page = "${queryVo.page}";
        if (page !== "${totalPages}") {
            document.getElementById("page").value = ++page;
            document.search.submit();
        }
    };
</script>

</body>
</html>
