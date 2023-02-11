$(()=>{
    //1. 주소url분석 
    //ex) http://192.168.0.37:5500/html/download.html?no=C0001
    //querystring얻기
    let queryStr = location.search.substring(1) //no=C0001
    let url =backURL + 'attach/download'
    $.ajax({
        xhrFields:{
            responseType: 'blob'//이미지 다운로드용 설정
             //이렇게 설정하면 아래의result는빌롭타입이 된다
        },
        cache: false, //이미지 다운로드용 설정
        url:url,
        method: 'get',
        data:queryStr,
        success: function(result){
            let blobStr = URL.createObjectURL(result)
            $('div.download>img').attr('src', blobStr)

        },
        error: function(){

        }
    })

    //$.ajax로 상세정보얻기
    $('div.detail>div.attach').html('C0006.jpg') //첨부파일명보여주기

     //--첨부파일 클릭되었을때 할일 START--
     $('div.detail>div.attach').click((e)=>{
        location.href=backURL + 'attach/download?opt=attachment&no=' + $(e.target).html()
    })
    //--첨부파일 클릭되었을때 할일 END--
})
