import styles from '../styles/DateTag.module.css'

const WeekTag = () => {

    const date = new Date();
    const months = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Xunio", "Xullo", "Agosto", "Setembro", "Outubro", "Novembro", "Decembro"];
    const mondayDate = new Date(date.setDate(date.getDate() - ((date.getDay()-1)%7)));
    const sundayDate = new Date(date.setDate(date.getDate() + (6-((date.getDay()-1)%7))));

    return (
        <div className={styles.weekTag}>
            Semana do {mondayDate.getDate()} de {months[mondayDate.getMonth()]} ao {sundayDate.getDate()} de {months[sundayDate.getMonth()]}
        </div>
    )

}

export default WeekTag;