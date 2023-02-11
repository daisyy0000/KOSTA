$(() => {
  
    $.ajax({
      url: backURL + "board/list",
      method: "GET",
      success: function (jsonObj) {
        console.log(jsonObj);
        let $origin = $("div.boardlist>div.board").first();
        let $parent = $("div.boardlist");
        let $repOrigin = $("div.reply").first();
        let $repParent = $("div.replylist");
        let replies = "";
        $(jsonObj).each((index, board) => {
          let boardNum = board.boardNum;
          let boardId = "";
          if (board.boardC != null) {
            boardId = board.boardC.id;
          }
          let boardDt = board.boardDt;
          let boardTitle = board.boardTitle;
          let boardContent = board.boardContent;
          let boardViewCnt = board.boardViewCnt;
          replies = board.replies;
          let $copy = $origin.clone();
          $copy.attr("id", "board_" + boardNum);
          //--썸네일 이미지 다운로드 START--
          $.ajax({
            xhrFields: {
              responseType: "blob",
            },
            cashe: false,
            url: backURL + "board/download",
            method: "get",
            data: "boardNum=" + boardNum + "&opt=inline&type=2",
            success: function (result) {
              let blobStr = URL.createObjectURL(result);
              $copy.find("div.thumbnail>img").attr("src", blobStr);
            },
          });
          //--썸네일 이미지 다운로드 END--
          $copy
            .find("div.boardNum")
            .html(
              "글번호 : " +
                boardNum +
                " 작성자 : " +
                boardId +
                " 조회수 : " +
                boardViewCnt
            );
  
          $copy
            .find("div.boardTitle")
            .html("제목 : " + boardTitle + " 내용 : " + boardContent);
  
          $copy.find("div.boardDt").html("작성일자 : " + boardDt);
  
          $copy
            .append('<div id="editBtn">')
            .append(
              '<input type="button" id="editBtn_' +
                boardNum +
                '"class="edit"value="수정">'
            );
          $copy.append(
            '<input type="button" id="delBtn_' +
              boardNum +
              '"class="del"value="삭제">'
          );
          // console.log(replies);
          if (replies.length != 0) {
            $(replies).each((index, reply) => {
              let boardReplyNum = reply.boardReplyNum;
              let boardReplyId = reply.boardReplyC.id;
              let boardReplyContent = reply.boardReplyContent;
              let boardReplyDt = reply.boardReplyDt;
              let level = reply.level;
              let emoji = "";
              for (let i = 0; i < level; i++) {
                emoji += "➡️";
              }
              let boardReplyParentNum = reply.boardReplyParentNum;
              $copy
                .find("div.reply")
                // .append('<div class="reply_list">')
                .append(
                  '<div class="boardreply"> <div class="boardReplyNum">' +
                    emoji +
                    "댓글번호 : " +
                    boardReplyNum +
                    " 댓글 작성자 : " +
                    boardReplyId +
                    "</div>" +
                    '<div class="boardReplyContent">' +
                    boardReplyContent +
                    "</div>" +
                    '<div class="boardReplyDt">' +
                    "작성일자 : " +
                    boardReplyDt +
                    "</div> </div>"
                );
              // .append("</div>");
            });
          }
          $parent.append($copy);
        });
        $origin.hide();
      },
      error: function (xhr) {
        alert(xhr.responseText);
      },
    });
    //--게시글 목록 불러오기 END--

    //--글 삭제 버튼 클릭 START-
    $(document).on("click", "input[class='del']", function () {
      let num = $(this).attr("id").split("_")[1];
      if (confirm("글을 삭제하시겠습니까?") == false) {
        return;
      }
      $.ajax({
        xhrFields: {
          withCredentials: true,
        },
        url: backURL + "board/" + num,
        method: "DELETE",
        timeout: 0,
        success: function (jsonObj) {
          console.log(jsonObj);
          location.reload(true);
        },
      });
    });
    //--글 삭제 버튼 클릭 END--
  });
  