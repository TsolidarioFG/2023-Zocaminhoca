import styles from '../styles/DateTag.module.css'

const DateTag = () => {

    const date = new Date();
    const months = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Xunio", "Xullo", "Agosto", "Setembro", "Outubro", "Novembro", "Decembro"];
    const days = ["Luns", "Martes", "Mércores", "Xoves", "Venres", "Sabado", "Domingo"];

return ( 
    <div className={styles.dateTag}>
        {days[(date.getDay()-1)%7] + ", " + date.getDate() + " - " + months[date.getMonth()] + " - " + date.getFullYear()}
    </div>
)

}

export default DateTag;