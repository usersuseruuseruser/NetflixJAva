import {BrowserRouter, Route, Routes, useLocation} from "react-router-dom";
import Main from "./Pages/Main/Main.jsx";
import MainContent from "./Pages/MainContent/MainContent.jsx";
import PersonalInfoTab from "./Pages/PersonalAccount/PersonalInfoTab/PersonalInfoTab.jsx";
import PersonalReviewsTab from "./Pages/PersonalAccount/PersonalReviewsTab/PersonalReviewsTab.jsx";
import SelectionContent from "./Pages/SelectionContent/SelectionContent.jsx";
import SignUpSignIn from "./Pages/SignUp&SignIn/SignUpSignIn.jsx";
import ViewContent from "./Pages/ViewContent/ViewContent.jsx";
import Header from "./Pages/Shared/Header/Header.jsx";
import GeneralPart from "./Pages/PersonalAccount/GeneralPart/GeneralPart.jsx";
import Error404 from "./Pages/Error/Error404.jsx";
import "/src/Pages/Shared/Styles/App.css";
import {ToastContainer} from "react-toastify";
import {ProtectedRoute} from "./Pages/Shared/Security/ProtectedRoute.jsx";

function App() {
    
    const location = useLocation();

    return (
        <>
            <ToastContainer theme={"dark"} position={"bottom-center"}/>
            {location.pathname !== "/" && !location.pathname.includes("signin") 
                && location.pathname !== "/signup" && <Header/>}
            <Routes>
                <Route path="/" element={<Main/>}/>
                <Route path="MainContent" element={<MainContent/>}/>
                <Route path={"/PersonalAccount"} element={<ProtectedRoute roles={["user", "admin"]}/>}>
                    <Route path={"/PersonalAccount"} element={<GeneralPart/>}>
                        <Route index element={<PersonalInfoTab/>}/>
                        <Route path="PersonalInfoTab" element={<PersonalInfoTab/>}/>
                        <Route path="PersonalReviewsTab" element={<PersonalReviewsTab/>}/>
                    </Route>
                </Route>
                <Route path="SelectionContent" element={<SelectionContent/>}/>
                <Route path="signup" element={<SignUpSignIn formType="signup"/>}/>
                <Route path="signin" element={<SignUpSignIn formType="signin"/>}>
                    <Route path="google"/>
                    <Route path="vk"/>
                </Route>
                <Route path="ViewContent/:id" element={<ViewContent/>}/>
                <Route path="*" element={<Error404/>}/>
            </Routes>
        </>
    )
}

export default App
