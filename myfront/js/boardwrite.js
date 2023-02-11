$(()=>{
    //폼태그 안쪽에 있는 버튼은 input type=submit과 같음
    //버튼 클릭 이벤트 발생 -> 폼 서브밋 이벤트 발생

    //---form태그 안쪽에서 서밋 발생할 때 START
    //폼객체 찾기
    let $form = $('div.boardwrite>form')
    $form.submit(()=>{
        //파일업로드할 때는 폼 data 반드시 필요 첨부파일을 포함한 전송은
        let formdata = new FormData($form[0])
        
        var settings = {
            xhrFields: {
                withCredentials: true //크로스오리진을 허용!
             },
            "url": backURL + "board/write",
            "method": "POST",
            "processData": false, //파일업로드용 설정
            "mimeType": "multipart/form-data", //파일업로드용설정
            "contentType": false,
            "data": formdata
          };

          $.ajax(settings).done(function (response) {
            console.log(response);
          });
          return false; //기본이벤트처리 중지
    })
    

    //---form태그 안쪽에서 서밋 발생할 때 END
})