/*
 * Copyright (c) 2017-2018 THL A29 Limited, a Tencent company. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package www.jykj.com.jykj_zxyl.util.tencenUtil.models;

import www.jykj.com.jykj_zxyl.util.tencenUtil.common.AbstractModel;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import java.util.HashMap;

public class BizLicenseOCRResponse extends AbstractModel{

    /**
    * 注册号
    */
    @SerializedName("RegNum")
    @Expose
    private String RegNum;

    /**
    * 公司名称
    */
    @SerializedName("Name")
    @Expose
    private String Name;

    /**
    * 注册资本
    */
    @SerializedName("Capital")
    @Expose
    private String Capital;

    /**
    * 法定代表人
    */
    @SerializedName("Person")
    @Expose
    private String Person;

    /**
    * 地址
    */
    @SerializedName("Address")
    @Expose
    private String Address;

    /**
    * 经营范围
    */
    @SerializedName("Business")
    @Expose
    private String Business;

    /**
    * 主体类型
    */
    @SerializedName("Type")
    @Expose
    private String Type;

    /**
    * 营业期限
    */
    @SerializedName("Period")
    @Expose
    private String Period;

    /**
    * 组成形式
    */
    @SerializedName("ComposingForm")
    @Expose
    private String ComposingForm;

    /**
    * 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
    */
    @SerializedName("RequestId")
    @Expose
    private String RequestId;

    /**
     * Get 注册号 
     * @return RegNum 注册号
     */
    public String getRegNum() {
        return this.RegNum;
    }

    /**
     * Set 注册号
     * @param RegNum 注册号
     */
    public void setRegNum(String RegNum) {
        this.RegNum = RegNum;
    }

    /**
     * Get 公司名称 
     * @return Name 公司名称
     */
    public String getName() {
        return this.Name;
    }

    /**
     * Set 公司名称
     * @param Name 公司名称
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * Get 注册资本 
     * @return Capital 注册资本
     */
    public String getCapital() {
        return this.Capital;
    }

    /**
     * Set 注册资本
     * @param Capital 注册资本
     */
    public void setCapital(String Capital) {
        this.Capital = Capital;
    }

    /**
     * Get 法定代表人 
     * @return Person 法定代表人
     */
    public String getPerson() {
        return this.Person;
    }

    /**
     * Set 法定代表人
     * @param Person 法定代表人
     */
    public void setPerson(String Person) {
        this.Person = Person;
    }

    /**
     * Get 地址 
     * @return Address 地址
     */
    public String getAddress() {
        return this.Address;
    }

    /**
     * Set 地址
     * @param Address 地址
     */
    public void setAddress(String Address) {
        this.Address = Address;
    }

    /**
     * Get 经营范围 
     * @return Business 经营范围
     */
    public String getBusiness() {
        return this.Business;
    }

    /**
     * Set 经营范围
     * @param Business 经营范围
     */
    public void setBusiness(String Business) {
        this.Business = Business;
    }

    /**
     * Get 主体类型 
     * @return Type 主体类型
     */
    public String getType() {
        return this.Type;
    }

    /**
     * Set 主体类型
     * @param Type 主体类型
     */
    public void setType(String Type) {
        this.Type = Type;
    }

    /**
     * Get 营业期限 
     * @return Period 营业期限
     */
    public String getPeriod() {
        return this.Period;
    }

    /**
     * Set 营业期限
     * @param Period 营业期限
     */
    public void setPeriod(String Period) {
        this.Period = Period;
    }

    /**
     * Get 组成形式 
     * @return ComposingForm 组成形式
     */
    public String getComposingForm() {
        return this.ComposingForm;
    }

    /**
     * Set 组成形式
     * @param ComposingForm 组成形式
     */
    public void setComposingForm(String ComposingForm) {
        this.ComposingForm = ComposingForm;
    }

    /**
     * Get 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。 
     * @return RequestId 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     */
    public String getRequestId() {
        return this.RequestId;
    }

    /**
     * Set 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     * @param RequestId 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     */
    public void setRequestId(String RequestId) {
        this.RequestId = RequestId;
    }

    /**
     * Internal implementation, normal users should not use it.
     */
    public void toMap(HashMap<String, String> map, String prefix) {
        this.setParamSimple(map, prefix + "RegNum", this.RegNum);
        this.setParamSimple(map, prefix + "Name", this.Name);
        this.setParamSimple(map, prefix + "Capital", this.Capital);
        this.setParamSimple(map, prefix + "Person", this.Person);
        this.setParamSimple(map, prefix + "Address", this.Address);
        this.setParamSimple(map, prefix + "Business", this.Business);
        this.setParamSimple(map, prefix + "Type", this.Type);
        this.setParamSimple(map, prefix + "Period", this.Period);
        this.setParamSimple(map, prefix + "ComposingForm", this.ComposingForm);
        this.setParamSimple(map, prefix + "RequestId", this.RequestId);

    }
}

