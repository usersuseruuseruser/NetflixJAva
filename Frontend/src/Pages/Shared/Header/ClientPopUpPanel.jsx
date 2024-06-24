import {useNavigate} from "react-router-dom";
import "/src/Pages/Shared/Header/Styles/ClientPopUpPanel.css";
import {authenticationService} from "../../../services/authentication.service.js";
const ClientPopUpPanel = ({user}) => {
    const navigate = useNavigate()
    const navigateToPersonalAccount = (tabName) => {
        navigate("/PersonalAccount/" + tabName)
    }

  async function handleLogoutButtonClick() {
      await authenticationService.logout();
      navigate("/");
  }

  return(
        <div id="client-pop-up-panel">
            <label id="client-name" className="client-pop-up-panel-label">{!(user === null || user === undefined) ? user.name : null}</label>
            <label className="separator"></label>
            <label className="client-pop-up-panel-label" onClick={() => {
                navigateToPersonalAccount("PersonalInfoTab")
            }}>Личные данные</label>
            <label className="client-pop-up-panel-label" onClick={() => {
                navigateToPersonalAccount("PersonalReviewsTab")
            }}>Рецензии</label>
            <input id="client-pop-up-panel-sign-out-btn" type="button" value="Выйти" onClick={handleLogoutButtonClick}></input>
        </div>
    )
}
export default ClientPopUpPanel