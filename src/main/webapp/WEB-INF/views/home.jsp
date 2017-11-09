<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    th {text-align: right;}
    .error {color: red;}
</style>
<body>
<h2>日志文件解析完成！</h2>

<form id="search" name="search" action="/log/queryLog" method="post">
    <fieldset>
        <legend>日志查询</legend>
        <table align="">
            <tr>
                <th>开始时间:</th>
                <td><input type="text" name="timeStart" size="30" value="2015-05-28 16:13:46,000"></td>
            </tr>
            <tr>
                <th>截止时间:</th>
                <td><input type="text" name="timeEnd" size="30" value="2015-05-29 16:13:46,000"></td>
            </tr>
            <tr>
                <th>类名:</th>
                <td><input type="text" name="className" size="30" value="sender.EtermSession"></td>
            </tr>
            <tr>
                <th>日志级别:</th>
                <td>
                    <select name="level" >
                        <option value="INFO">INFO</option>
                        <option value="ERROR">ERROR</option>
                        <option value="DEBUG">DEBUG</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>日志内容（模糊查找）:</th>
                <td><input type="text" name="info" size="30" placeholder="--选填--"></td>
            </tr>
            <tr>
                <th colspan="2" style="text-align: center"><input type="submit"/></th>
            </tr>
        </table>
        <input type="hidden" name="page" value="1">

    </fieldset>
</form>

<script src="/staticfiles/jqvalidate/jquery-1.11.1.js"></script>
<script src="/staticfiles/jqvalidate/jquery.validate.min.js"></script>
<script src="/staticfiles/jqvalidate/messages_zh.min.js"></script>
<script>
    $.validator.setDefaults({
        submitHandler: function() {
            document.search.submit();
        }
    });

    $.validator.addMethod("timeStamp", function(value, element) {
        var timeStamp = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2},\d{3}$/
        return this.optional(element) || (timeStamp.test(value));
    }, "请正确填写日期（格式：yyyy-MM-dd HH:mm:ss,SSS）");

    $.validator.addMethod("clzName", function(value, element) {
        var clzName = /^(\w*\.)+\w+$/
        return this.optional(element) || (clzName.test(value));
    }, "请正确填写类名（格式：包名.类名）");

    $().ready(function() {
        $("#search").validate({
            rules: {
                timeStart: {
                    required: true,
                    timeStamp: true
                },
                timeEnd:  {
                    required: true,
                    timeStamp: true
                },
                className: {
                    required: true,
                    clzName: true
                }
            }
        });
    });
</script>


</body>
</html>
