let container = document.getElementById('container')

toggle = () => {
	container.classList.toggle('sign-in')
	container.classList.toggle('sign-up')
}

setTimeout(() => {
	container.classList.add('sign-in')
}, 200)

window.addEventListener('DOMContentLoaded', async (event) => {
   var datas=await fetch("http://localhost:8081/Login/AuthServlet",{method:"post"});
	var result=await datas.json();
	if(result["status"]=="success" && datas.status==200){
		console.log("Status Success")
		return true;
	}else{
		console.log("Status Failure")
		return false;
	}
});
