//初始化Markdown编辑器
var contentEditor;
// 入口函数
$(function () {

        contentEditor = editormd("md-content", {
            width   : "100%",
            height  : 640,
            syncScrolling : "single",
            path    : "/lib/editormd/lib/",
            saveHTMLToTextarea : true,//注意3：这个配置，方便post提交表单

            emoji : true,
            taskList : true,
            tocm            : true,         // Using [TOCM]
            tex : true,                   // 开启科学公式TeX语言支持，默认关闭
            flowChart : true,             // 开启流程图支持，默认关闭
            sequenceDiagram : true,

            /**上传图片相关配置如下*/
            imageUpload : true,
            imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL : "/upload",       //注意你后端的上传图片服务地址
            onload: function() {
                // 引入插件 执行监听方法,粘贴上传图片
                editormd.loadPlugin("/lib/editormd/plugins/image-handle-paste/image-handle-paste", function(){
                    contentEditor.imagePaste();
                });
            }
        });


        //博客的保存和发布，true为发布，false为保存
    $('#save-btn').click(function () {
        $('[name="published"]').val(false);
        $('#blog-form').submit();
    });
    $('#publish-btn').click(function () {
        $('[name="published"]').val(true);
        $('#blog-form').submit();
    });

    //输入框限制
    $('.ui.form').form({
        fields : {
            title : {
                identifier: 'title',
                rules: [{
                    type : 'empty',
                    prompt: '标题：请输入博客标题'
                }]
            },
            content : {
                identifier: 'content',
                rules: [{
                    type : 'empty',
                    prompt: '标题：请输入博客内容'
                }]
            },
            typeId : {
                identifier: 'type.id',
                rules: [{
                    type : 'empty',
                    prompt: '标题：请选择博客分类'
                }]
            },
            typeId : {
                identifier: 'type.id',
                rules: [{
                    type : 'empty',
                    prompt: '标题：请选择博客标签'
                }]
            },
            firstPicture : {
                identifier: 'firstPicture',
                rules: [{
                    type : 'empty',
                    prompt: '标题：请输入博客首图'
                }]
            }
        }
    });

});