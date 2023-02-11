$(() => {
    //--첨부파일이 변경되었을때 할일 START--
    let $divShow = $('div.show')
    $('div.form>form>input[type=file]').change((e) => {
        $divShow.empty()
        //파일이 여러개가 될 수도 있으니 []
        $(e.target.files).each((index, imgFileObj) => {


            // let imgFileObj = e.target.files[0]

            console.log(imgFileObj)
            //blob타입의 이미지파일객체내용을 문자열로 변환
            let blobStr = URL.createObjectURL(imgFileObj)
            // $('div.show>img').attr('src', blobStr)
            let img = $('<img>').attr('src', blobStr).css('margin-left', '5px')
            $divShow.append(img)
        })
    })
    //--첨부파일이 변경되었을때 할일 END--


    //--폼 서브밋되었을때 할일 START--
    let $form = $('div.form>form')
    $form.submit(() => {
        let url = backURL + 'attach/upload'

        //방법1: let formData = new FormData()
        //방법2: 폼태그안 속성들을 불러와서 객체로 만들기

        // let formData = new FormData()
        let formData = new FormData($form[0])
        formData.forEach((value, key) => {
            console.log(key)
            console.log(value)
            console.log('------')
        })
        $.ajax({
            url: url,
            method: 'post',//파일업로드용 설정
            data: formData, //파일업로드용 설정
            processData: false,//파일업로드용 설정
            contentType: false,//파일업로드용 설정
            success: function () {

            },
            error: function (xhr) {
                alert('오류' + xhr.status)
            }
        })
        return false
    })
    //--폼 서브밋되었을때 할일 END--

})