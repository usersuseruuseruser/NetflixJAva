import styles from './styles/page.module.scss';
import {Section} from "./components/Section.jsx";
import { register } from 'swiper/element/bundle';
import {PromoSlider} from "./components/PromoSlider.jsx";
import {useEffect, useState} from "react";
import {contentService} from "../../services/content.service.js";
register();


const MainContent = () => {

    const [sectionsData, setSectionsData] = useState([]);

    useEffect(() => {
        (async () => {
            const {response, data} = await contentService.getSections();
            if (response.ok) {
                setSectionsData(data);
            }
            console.log(data);
        })()
    }, []);
    
    return (
        <div className={styles.pageWrapper}>
            <PromoSlider></PromoSlider>
            <div className={styles.sectionsList}>
                {sectionsData.map(((sectionData, index) => (
                    <Section sectionData={sectionData} key={index}/>
                )))}
            </div>
        </div>
    )
}

export default MainContent