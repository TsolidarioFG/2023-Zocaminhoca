import styles from '../styles/HeaderWelcome.module.css'
import DateTag from './DateTag';
import WeekTag from './WeekTag';

const HeaderWelcome = () => {

    const date = new Date();

    return (
        <div className={styles.welcomeContainer}>
            <DateTag/>
            <div className={styles.welcome}>
                B E N V I D A !
            </div>
            <WeekTag/>
        </div>
    )

}

export default HeaderWelcome;