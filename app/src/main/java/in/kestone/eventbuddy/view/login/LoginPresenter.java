/*
 *    Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package in.kestone.eventbuddy.view.login;


import in.kestone.eventbuddy.data.DataManager;
import in.kestone.eventbuddy.view.base.BasePresenter;

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V> {

    LoginPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void startLogin(String emailId, int id, String name, String designation, String imagePath,
                           String organization, String mobile) {
        getDataManager().saveDetail(id, name, emailId, designation, imagePath, organization, mobile);
        getDataManager().setLoggedIn();
        getMvpView().openMainActivity();
//        getMvpView().openCheckInActivity();
    }

}
