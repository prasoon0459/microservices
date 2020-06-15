function openadd(){
    closeupdate();
    closedelete();
    document.getElementById("add_username").focus();
    document.getElementById("addform").style.display = "block";
}
function closeadd(){
    document.getElementById("add-container").reset();
    document.getElementById("addform").style.display = "none";
}

function openupdate(){
    closeadd();
    closedelete();
    document.getElementById("update_username").focus();
    document.getElementById("updateform").style.display = "block";
}
function closeupdate(){
    document.getElementById("update-container").reset();
    document.getElementById("updateform").style.display = "none";
}

function opendelete(){
    closeadd();
    closeupdate();
    document.getElementById("delete_username").focus();
    document.getElementById("deleteform").style.display = "block";
}
function closedelete(){
    document.getElementById("delete-container").reset();
    document.getElementById("deleteform").style.display = "none";
}

