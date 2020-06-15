function logout(){
    console.log('logout called');
    $.ajax({'url':"http://10.167.80.144/auth/refresh/logout",'type': "POST",
    success: function (response){
        console.log('logout');
        sessionStorage.clear();
        localStorage.removeItem("TTLRefresh");
    },
    error: function (xhr, status){
        console.log(xhr.status);
        alert('Could not logout');
    }});
}