function openadd(){
    closeupdate();
    closedelete();
    document.getElementById("addform").style.display = "block";
}
function closeadd(){
    document.getElementById("addform").style.display = "none";
}

function openupdate(){
    closeadd();
    closedelete();
    document.getElementById("updateform").style.display = "block";
}
function closeupdate(){
    document.getElementById("updateform").style.display = "none";
}

function opendelete(){
    closeadd();
    closeupdate();
    document.getElementById("deleteform").style.display = "block";
}
function closedelete(){
    document.getElementById("deleteform").style.display = "none";
}

