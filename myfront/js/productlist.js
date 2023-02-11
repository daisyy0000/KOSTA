//돔트리가 완성될때까지 기다려
$(() => {
    function showList(url, page) {
        let $origin = $('div.product').first()

        //product에서 원본(origin)인 것만 빼고 삭제
        $('div.product').not(':first-child').remove() //직전페이지에 출력되었던 내용이 쌓이는 걸 막아주는 (2페이지는 6개..3페이지 9개)
        //첫번째 차일드가 뭐에요? 디브프로덕트는 지우나 퍼스트차일드 지우지마라
        
        //원본이 보여지는상태야 복제본도 보여지는 상태가 되니
        $origin.show()
        //$.ajax 비동기요청은 요청보내놓고 다음 내용 수행하러 내려감!
        $.ajax({
            url: url,
            method: 'get',
            data: 'currentPage=' + page,

            //응답이 성공했다는 것 뿐 기능이 성공했다는 것은 아님
            success: function (jsonObj) {
                let list = jsonObj.list;
                let totalPage = jsonObj.totalPage;
                let currentPage = jsonObj.currentPage;
                let startPage = jsonObj.startPage
                let endPage = jsonObj.endPage


                let $parent = $('div.productlist')
                $(list).each((index, p) => {
                    let prodNo = p.prodNo;
                    let prodName = p.prodName;
                    let prodPrice = p.prodPrice;
                    let $copy = $origin.clone();

                    //1. 직접 문자열을 
                    // let imgStr = '<img src="../images/'+ prodNo +'.jpg">'
                    // $copy.find('div.img').html(imgStr)

                    //2. 제이쿼리를 img객체를 생성해서 append
                    let $imgObj = $('<img>')
                    $imgObj.attr('src', '../images/' + prodNo + '.jpg')
                    $copy.find('div.img').empty().append($imgObj)
                    //empty: img객체내용을 비우고(기본값"이미지") 
                    $copy.find('div.prodNo').html(prodNo)
                    $copy.find('div.prodName').html(prodName)
                    $copy.find('div.prodPrice').html(prodPrice + '원')

                    //복제를 하고 원하는위치에 붙여줘야하니
                    $parent.append($copy)


                })
                //원본은 화면에서 사라지게 만들기
                $origin.hide()


                let $pageGroup = $('div.pagegroup')
                let pagegroupStr = ''
                if (startPage > 1) {
                    pagegroupStr += '<span class="' + (startPage - 1) + '">[이전]</span>'
                }

                //3페이지가 끝인데 4가 자동으로 뜨니 반복문 들어가기 전
                if (endPage > totalPage) {
                    endPage = totalPage
                }

                for (let i = startPage; i <= endPage; i++) {
                    if(i == currentPage){
                        pagegroupStr += '<span class="current ' + i + '">[' + i + ']</span>'
                    }else{
                        pagegroupStr += '<span class="' + i + '">[' + i + ']</span>'
                    }
                   
                }

                if (endPage < totalPage) {
                    pagegroupStr += '<span class="' + (endPage + 1) + '">[다음]</span>'
                }

                //넣기
                $pageGroup.html(pagegroupStr)
            },

            error: function (xhr) {
               //alert(xhr.status)
               let obj = JSON.parse(xhr.responseText)
               alert(jsonObj.msg)
            }
        })
    }





    // url에서 필요한 인자가 자바스크립트객체
    let url = backURL+'/product/list';

    // 상품목록 요청 start
    showList(url, 1)
    // 상품목록 요청 end

    // 페이지번호클릭 start

    //아직 span객체는 존재하지 않는 상태. 최초로 돔트리가 완성될때 존재하지 않는 객체   
    //작동되지 않음 
    //$('div.pagegroup span').click(()=>{
    //    alert('클릭됨')
    //})

    //해결방법 on함수

    //현재페이지(current 속성인 span)를 제외한 span만 적용시키게 하기
    $('div.pagegroup').on('click', 'span:not(.current)', (e) => { //매개변수 이름은상관 X
        //alert('클릭됨')
        let page = $(e.target).attr('class')
        showList(url, page)
        
    })
    // 페이지번호클릭 end


    //--상품클릭후 상세페이지 start--
    $('div.productlist').on('click', 'div.product', (e)=>{

        //parents: 현재객체입장에서 부모나 조상객체를 찾는 메서드
        //parent: 바로 위 부모
        //클릭한 상품번호를 얻어오는 작업
        let prodNo = $(e.target).parents('div.product').find('div.prodNo').html()
        location.href='./productinfo.html?prodNo=' + prodNo
    })


    //--상품클릭후 상세페이지 end--
})