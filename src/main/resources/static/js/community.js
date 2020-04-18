/*
*       回复
* */

function post() {
    var url = window.location.href;
    var parentId = url.substring(url.lastIndexOf('/') + 1);
    var content = $("#comment_content").val();
    comment(parentId,content,1);
}

function comment(parentId,content,type) {
    if(!content){
        alert("不能回复空信息！");
        return;
    }

    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:'application/json',
        data:JSON.stringify({
            "parentId":parentId,
            "content":content,
            "type":type
        }),
        success:function (response) {
            if(response.code == 200){
                $("#comment-section").hide();
                window.location.reload();
            }else {
                if(response.code == 2003){
                    //当前未登录
                    var isAccept = confirm(response.message);
                    if(isAccept){
                        window.open("https://github.com/login/oauth/authorize?client_id=4f1cd9bf93164d6be1b7&redirect_uri=http://localhost:9999/callback&scope=user&state=1");
                        window.localStorage.setItem("closable","true");
                    }

                }else{
                    alert(response.message);
                }

            }
        },
        dataType:"json"
    });
}

/*
*    展开二级评论
* */
function collapseComment(e) {
    var id = e.getAttribute("data-id");
    var collapse = e.getAttribute("data-collapse");
    if(collapse){
        //折叠二级评论
        $("#comment-"+id).removeClass("in");
        e.removeAttribute("data-collapse");
    }else{
        //展开二级评论
        $("#comment-"+id).addClass("in");
        e.setAttribute("data-collapse","in");
        // $.ajax({
        //     type:"GET",
        //     url:"/comment/"+id,
        //     contentType:'application/json',
        //     success:function(response){
        //         // alert(JSON.stringify(response));
        //         console.log(response);
        //     }
        // });
    }
}

/*
*       回复
* */

function reply(e){
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    console.log(commentId);
    console.log(content);
    comment(commentId,content,2);

}