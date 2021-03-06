package com.wning.demo.net.okhttp;

import java.util.List;

/**
 * Created by wning on 2018/6/26.
 */

public class GameInfo {


    /**
     * message : SUCCESS
     * content : {"list":[{"icon":"http://resource.qxiu.com/20150629/593aaa198e07446d70a1.png","actId":"107","title":"豪车漂移","navshow":"0","init":{"roll":0,"interval":30,"status":1},"from":"1","type":"1","showtype":"0","url":"http://mcar.qxiu.com?noblelevel=0&money=0","jump":"1"},{"icon":"http://resource.qxiu.com/20160128/2c420b9999a5e89c3277.png","actId":"100","title":"海盗寻宝","navshow":"0","init":{"status":1},"from":"1","type":"2","showtype":"0","url":"http://hot.active.qxiu.com/award20150601/mobile/index.html","jump":"0"}]}
     * state : 0
     */

    private String message;
    private ContentBean content;
    private int state;

    @Override
    public String toString() {
        return "GameInfo{" +
                "message='" + message + '\'' +
                ", content=" + content +
                ", state=" + state +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static class ContentBean {
        private List<ListBean> list;

        @Override
        public String toString() {
            return "ContentBean{" +
                    "list=" + list +
                    '}';
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * icon : http://resource.qxiu.com/20150629/593aaa198e07446d70a1.png
             * actId : 107
             * title : 豪车漂移
             * navshow : 0
             * init : {"roll":0,"interval":30,"status":1}
             * from : 1
             * type : 1
             * showtype : 0
             * url : http://mcar.qxiu.com?noblelevel=0&money=0
             * jump : 1
             */

            private String icon;
            private String actId;
            private String title;
            private String navshow;
            private InitBean init;
            private String from;
            private String type;
            private String showtype;
            private String url;
            private String jump;

            @Override
            public String toString() {
                return "ListBean{" +
                        "icon='" + icon + '\'' +
                        ", actId='" + actId + '\'' +
                        ", title='" + title + '\'' +
                        ", navshow='" + navshow + '\'' +
                        ", init=" + init +
                        ", from='" + from + '\'' +
                        ", type='" + type + '\'' +
                        ", showtype='" + showtype + '\'' +
                        ", url='" + url + '\'' +
                        ", jump='" + jump + '\'' +
                        '}';
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getActId() {
                return actId;
            }

            public void setActId(String actId) {
                this.actId = actId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getNavshow() {
                return navshow;
            }

            public void setNavshow(String navshow) {
                this.navshow = navshow;
            }

            public InitBean getInit() {
                return init;
            }

            public void setInit(InitBean init) {
                this.init = init;
            }

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getShowtype() {
                return showtype;
            }

            public void setShowtype(String showtype) {
                this.showtype = showtype;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getJump() {
                return jump;
            }

            public void setJump(String jump) {
                this.jump = jump;
            }

            public static class InitBean {
                /**
                 * roll : 0
                 * interval : 30
                 * status : 1
                 */

                private int roll;
                private int interval;
                private int status;

                public int getRoll() {
                    return roll;
                }

                public void setRoll(int roll) {
                    this.roll = roll;
                }

                public int getInterval() {
                    return interval;
                }

                public void setInterval(int interval) {
                    this.interval = interval;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }
            }
        }
    }
}
