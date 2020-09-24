package com.sakura.ydhyfinal.gsonres;

public class GetbookListP2 {

    private String code;
    private String result;
    public GetbookListP2_data data;

    public GetbookListP2_data getData() {
        return data;
    }

    public void setData(GetbookListP2_data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GetbookListP2{" +
                "data=" + data +
                '}';
    }


}
