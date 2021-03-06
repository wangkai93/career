#Git工作流
*本项目使用了Git仓库进行代码托管，本地使用SourceTree图形化工具进行相关Git仓库操作。运行的工作流如下：*

##第一步：克隆/新建仓库
&nbsp;&nbsp;&nbsp;&nbsp;首先，开发者欲加入开发团队当中，需要使用Git工具选择克隆远程仓库中的项目到本地仓库，或者新建一个本地仓库后拉取远程仓库的代码。


##第二步：建立开发分支
&nbsp;&nbsp;&nbsp;&nbsp;当开发者克隆完远程仓库项目后，需要新建自己的相应Master分支与Develop分支。一般而言，Develpo分支供开发者自行开发使用，当发布稳定版模块后，可以合并到Master分支，并发出post请求推送至远程Git库。

##第三步：开发程序模块
&nbsp;&nbsp;&nbsp;&nbsp;在本地Develop分支中开发自己的程序模块，测试完毕后，撰写简明扼要的提交信息，注明开发功能，commit到本地库。选择合并到本地Master分支。

##第四步：发起Pull请求

&nbsp;&nbsp;&nbsp;&nbsp;拉取远程库的最新版程序到本地库，核对有无冲突文件，有则进入文件状态视图代码合并工作。无则直接进行第六步操作。

##第五步：合并冲突
&nbsp;&nbsp;&nbsp;&nbsp;分支开发完成，发起Pull请求时，很可能有一堆文件有冲突，此时选择SourceTree的“文件状态”，对含有冲突的文件使用外部编辑器打开查看，解决代码中冲突部分，保存后选择“标记为已解决冲突”。当然，如果确定本地或远程版本的正确性，可以选择“使用他人版本解决冲突”或者“使用我的版本解决冲突”。合并完成后文件不含橙色感叹号，可以添加提交信息后commit到本地库。

##第六步：推送到远程仓库
合并冲突后，可以再发起Pull请求查看是否还有冲突文件，没有就可以推送当前分支到远程仓库了。当rebase以后，分支历史改变了，跟远程分支不一定兼容，有可能要强行推送，注意选择推送选项。

##
**后续程序模块开发时，只需要执行第三步至第六步的操作！**
##