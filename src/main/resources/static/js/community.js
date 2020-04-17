function post() {
    var url = window.location.href;
    var parentId = url.substring(url.lastIndexOf('/') + 1);
    var content = $("#comment_content").val();
    $.ajax({
       type:"POST",
       url:"/comment",
       contentType:'application/json',
       data:JSON.stringify({
            "parentId":parentId,
            "content":content,
            "type":1
       }),
       success:function (response) {
           if(response.code == 200){
            $("#comment-section").hide();
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