function logout(){
    console.log('logout called');
    $.ajax({'url':"http://10.167.80.144/auth/refresh/logout",'type': "POST",
    'contentType':"application/json",dataType: "json",
    success: function (response) {
        console.log('logout');
        sessionStorage.clear();
        localStorage.removeItem("TTLRefresh");
        window.location.replace('index.html');
    },
    error: function (xhr, status){
        console.log(xhr);
        alert('Could not logout');
    }});
}