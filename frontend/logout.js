function logout(){
    console.log('logout called');
    $.ajax({'url':"http://bits.poc.com/auth/refresh/logout",'type': "POST",
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