function logout(){
    console.log('logout');
    sessionStorage.clear();
    window.location.replace('index.html')    
}