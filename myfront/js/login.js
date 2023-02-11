$(() => {
    //--form객체의 submit 이벤트 발생했을 때 할일 start
    $('div.login>form').submit(() => {
        let idValue = $('input[name=id]').val()
        let pwd = $('input[name=pwd]').val()
        let url = backURL + 'customer/login'
        let data = $('div.login>form').serialize()
        console.log('serialize값: ' + data)
        $.ajax({
            xhrFields: {
                withCredentials: true //크로스오리진을 허용!
             },
            url:url,
            method: 'post',
            data: data,
                  //방법3: $('div.login>form').serialize()
                  //방법2: {id: idValue, pwd: pwdValue}
                  //방법1: 'id='+idValue +'&pwd='+pwdValue
            success: function(jsonObj){  //: 상태코드가 200번일때만!
                //responseEntity사용함으로써 바뀐 코드
                location.href = frontURL
                // console.log(jsonObj)
                // if(jsonObj.status == -1){
                //     alert('로그인실패')
                // }else{
                //     //location.href = frontURL
                //     showMenuAtLogined()
                }, error: function(xhr){
                    alert(xhr.responseText)
                }
        })
        //하는이유: form태그의 action속성 URL로 전달하려는 내장된 기능을 막으려고
        //쉽게말해 해당 태그의 기본이벤트를 막으려는 목적!
        return false
        //이걸 안하고 폼태그의 액션속성도 없으면 지금사용중인 URL로 다시전송됨
        //e.preventDefault(): 기본이벤트처리를 안함.
        //e.stopPropagation(): 이벤트전파를 중지.
        //e는 submit((e)=>)!
        //ajax구문에서 오타나 오류가 나면 return false까지 못오니깐 기본이벤트를 처리하러 자꾸현재페이지 가려고해서깜박깜박 
    })
    //--form객체의 submit 이벤트 발생했을 때 할일 end
})