//初始化Markdown编辑器
var contentEditor;
// 入口函数
$(function () {
       // 手机端汉堡按钮的显示和隐藏
    $('.menu.toggle').click(function () {
        $('.m-item').toggleClass('m-mobile-hide');
    });

    //    -----------adminBlog和adminWriteBlog公共----------------
        //点击头像注销
    $('.ui.dropdown').dropdown({
    });

    //消息提示关闭初始化
    $('.message .close')
        .on('click', function () {
            $(this)
                .closest('.message')
                .transition('fade');
        });

//    网站运行时间显示
    function secondToDate(second) {
        if (!second) {
            return 0;
        }
        var time = new Array(0, 0, 0, 0, 0);
        if (second >= 365 * 24 * 3600) {
            time[0] = parseInt(second / (365 * 24 * 3600));
            second %= 365 * 24 * 3600;
        }
        if (second >= 24 * 3600) {
            time[1] = parseInt(second / (24 * 3600));
            second %= 24 * 3600;
        }
        if (second >= 3600) {
            time[2] = parseInt(second / 3600);
            second %= 3600;
        }
        if (second >= 60) {
            time[3] = parseInt(second / 60);
            second %= 60;
        }
        if (second > 0) {
            time[4] = second;
        }
        return time;
    }
    function setTime() {
        var create_time = Math.round(new Date(Date.UTC(2020, 02, 20, 12, 30, 30)).getTime() / 1000);
        var timestamp = Math.round((new Date().getTime() + 8 * 60 * 60 * 1000) / 1000);
        currentTime = secondToDate((timestamp - create_time));
        currentTimeHtml = '博客已经运行了：' + currentTime[0] + '年 ' + currentTime[1] + '天 '
            + currentTime[2] + '时 ' + currentTime[3] + '分 ' + currentTime[4]
            + '秒';
        document.getElementById("htmer_time").innerHTML = currentTimeHtml;
    }
    setInterval(setTime, 1000);


});