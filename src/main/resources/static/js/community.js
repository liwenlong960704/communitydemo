/*
*       评论
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
    var comments = $("#comment-"+id);
    var collapse = e.getAttribute("data-collapse");
    if(collapse){
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
    }else{
        var subCommentContainer = $("#comment-"+id);
        if(subCommentContainer.children().length == 1){
            //二级评论加载
            $.getJSON("/comment/"+id , function(request){
                $.each(request.data.reverse(),function(index, comment){
                    var mediaLeftElement = $("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>",{
                        "class":"media-heading comment-heading",
                        "html":comment.user.username
                    })).append($("<span/>",{
                        "html":comment.content
                    })).append($("<span/>",{
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-DD HH:mm:ss')
                    }));

                    var mediaElement = $("<div/>",{
                        "class":"media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
            });
        }
        comments.addClass("in");
        e.setAttribute("data-collapse","in");
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

/*
*   选择标签
*
* */
function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if(previous){
        var tags = previous.split(',');
        if(tags.indexOf(value) == -1){
            $("#tag").val(previous+','+value);
        }
    }else{
        $("#tag").val(value);
    }
}

function showSelectTag() {
    $("#select-tag").show();
}
