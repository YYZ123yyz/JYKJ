package entity;

import java.util.List;

public class DocterFriendEntity {
    private List<DocterFriendChildEntity> mChildList;                   //
    private    String           mGroupTitle;
    private     boolean          mClick;                                     //是否点击了

    public boolean ismClick() {
        return mClick;
    }

    public void setmClick(boolean mClick) {
        this.mClick = mClick;
    }

    public String getmGroupTitle() {
        return mGroupTitle;
    }

    public void setmGroupTitle(String mGroupTitle) {
        this.mGroupTitle = mGroupTitle;
    }

    public List<DocterFriendChildEntity> getmChildList() {
        return mChildList;
    }

    public void setmChildList(List<DocterFriendChildEntity> mChildList) {
        this.mChildList = mChildList;
    }
}
