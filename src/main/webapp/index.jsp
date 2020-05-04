<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>页面</title>
    <meta name="viewport" content="UTF-8">
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <script src="https://cdn.jsdelivr.net/npm/vue"></script>
    <script src="http://libs.baidu.com/jquery/1.9.0/jquery.js" type="text/javascript"></script>
    <script src="https://unpkg.com/element-ui/lib/index.js"></script>
</head>
<style>
    .attachment-remove {
        font-size: 25px;
        color: red;
        margin-left: 5px;
        cursor: pointer;
    }

    .attachment-text-p {
        border: 1px solid #c2cad8;
        padding: 5px 5px;
        margin: 0;
        float: left;
        height: 30px;
        width: 90%;
    }

    .attachment-text-p + i {
        float: left;
        line-height: 30px !important;
    }

    .input-attachment {
        width: 90% !important;
        padding: 4px 12px !important;
    }
</style>

<body>
<form id="attachments" enctype="multipart/form-data" class="form-horizontal nice-validator n-yellow" novalidate="novalidate">
    <div class='form-body'>
        <div class='form-group'>
            <label class="control-label col-md-1">附件管理：</label>
            <div class="col-md-4">
                <button id="attachmentAddBtn" type="button" class="btn btn-default">Add Attachment</button>
                <button id="attachmentDeleteBtn" type="button" class="btn btn-default">Delete Attachment</button>
                <button id="attachmentUploadBtn" type="button" class="btn btn-default">Upload</button>
            </div>
        </div>
        <div class='form-group'>
            <label class="control-label col-md-1">附件上传：</label>
            <div id="attachmentInputs" class="col-md-3">

            </div>
        </div>
    </div>
</form>

<script>
    //attachment-remove
    $("#attachmentInputs").on("click", ".attachment-remove", function (even) {
        $(this).prev().remove();//删除上一个兄弟节点
        $(this).remove();//删除自己
    });

    //add but
    $("#attachmentAddBtn").click(function (even) {
        //name值一样就可以
        $("#attachmentInputs").append("<input name=\"attachment\" type=\"file\" class=\"form-control input-attachment\"/><i class=\"fa fa-times attachment-remove\"></i>");
    });

    //delete
    $("#attachmentDeleteBtn").click(function (even) {
        var files = $("#attachmentInputs input[type='file']");
        files.each(function (index, element) {
            //从最下面开始删除，至少保留一个
            if (!(index === 0) && index === (files.length - 1)) {
                $(element).next().remove();
                $(element).remove();
            }
        });
    });

    //upload
    $("#attachmentUploadBtn").click(function (even) {

        //2、从零开始创建FormData对象 手动注入
        var formData = new FormData();
        var files = $("#attachmentInputs input[type='file']");
        // for (var i = 0; i < files.length; i++) {
        //     //注意：这里append进去的是File对象，而不是FileList对象
        //     formData.append("file", files[i].files[0]);
        // }
        formData.append("id","1");
        console.log(formData.getAll("file"));

        //执行上传
        $.ajax({
            url: "http://192.168.20.110:1553/dwart/theTeaching/takePartInMatch/updateTakePartInMatch",
            type: "post",
            data: formData,
            processData: false,
            contentType: false,
            headers:{"Authorization":"ZXlKaGJHY2lPaUpJVXpJMU5pSjkuZXlKMWMyVnlTVzVtYnlJNlcxMHNJbTlpYWlJNmV5SndZWE56ZDI5eVpGTjBZWFIxY3lJNmRISjFaU3dpZFc1cGIyNXBaQ0k2SWt0RVYzbGFTR3hCYVZORFpIQnlUM1IzVmpKelZUaEJhVVZwUlNJc0luTmxlQ0k2SXVlVXR5SXNJbU55WldGMFpWUnBiV1ZUZEdGdGNDSTZNVFUzT0RnMk9URTVOREF3TUN3aWJXOWlhV3hsSWpvaU1UZzNOelEwT1RBMk16Z2lMQ0poWTNScGRtVWlPblJ5ZFdVc0ltVnRjR3h2ZVdWbFNXUWlPaUl4TWpFMk5qRXlORGswT1RFMU5qa3dORGsySWl3aWNtVnRZWEpySWpvaUlpd2lZWFpoZEdGeUlqb2lJaXdpZEhsd1pTSTZJa1JFSWl3aWRYQmtZWFJsVkdsdFpWTjBZVzF3SWpveE5UZ3lOVEF4TnpRME1EQXdMQ0p3WVhOemQyOXlaQ0k2SW1Zek56bGxZV1l6WXpnek1XSXdOR1JsTVRVek5EWTVaREZpWldNek5EVmxJaXdpYW05aWJuVnRZbVZ5SWpvaUlpd2lhR2x5WldSRVlYUmxJam94TlRZeE16a3lNREF3TURBd0xDSnVZVzFsSWpvaTZabTI1WTJENTZhbklpd2lZV3hzYjNkTWIyZHBiaUk2ZEhKMVpTd2laWGh3YVhKbFJHRjBaU0k2TVRVNE9Ea3hPRGcyTnpBd01Dd2lkR1ZzSWpvaU5UYzJNeUlzSW1SbGNIUkpaSE1pT2lKYk56WXpORGt6TVRBc01UQTJPRFl4TVRnNUxERXlOakV5TlRVNU55d3hNemMxTlRVNE5EWmRJaXdpY0c5emFYUnBiMjRpT2lKS1lYWmg1YnlBNVktUklpd2laVzFoYVd3aU9pSWlMQ0ozYjNKclVHeGhZMlVpT2lJaUxDSnZjbVJsY2lJNklqRTNOak0wTnpFd01qTTNPRFV5T0RVd055SXNJbk4wWVhSMWN5STZkSEoxWlgwc0ltMXZZbWxzWlNJNklqRTROemMwTkRrd05qTTRJaXdpYm1GdFpTSTZJdW1adHVXTmctZW1weUlzSW1sa0lqb2lNVEl4TmpZeE1qUTVORGt4TlRZNU1EUTVOaUlzSW1WNGNDSTZNVFU0T0RreE9EZzJOeXdpYVdGMElqb3hOVGcwTmpnM016QXpMQ0p1WW1ZaU9qRTFPRFEyT0Rjek1ETjkuUEVnWEpxaHFIVDRrYnk2UDZud1pETlI5WkJYZWpQU0FPRVZ6YVVmMElmZw"},
            success: function (data) {
            },
            error: function (e) {
            }
        });
    });

    //add one input
    $("#attachmentAddBtn").click();
</script>
</body>
</html>
