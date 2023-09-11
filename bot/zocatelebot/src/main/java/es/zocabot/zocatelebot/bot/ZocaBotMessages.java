package es.zocabot.zocatelebot.bot;

import es.zocabot.zocatelebot.model.dtos.OrderLineDto;
import es.zocabot.zocatelebot.model.dtos.OrderDto;
import es.zocabot.zocatelebot.model.dtos.ReceiptDto;

import java.util.Objects;

public abstract class ZocaBotMessages {

    public static String newUserMessage(String name) {

        String message = "Grazas por usar ZocaminhocaBot";
        if(!Objects.isNull(name)) {
            message = message + " " + name;
        }
        message += ", pronto actualizaremos o teu usuario para que podas utilizar todas as opcions do Bot. Un saúdo! =)";

        return message;
    }

    public static String orderMessage(OrderDto order) {

        String message = "Esta é a tua lista da compra para aa semana do " + order.getMondayDate() + ", ao " + order.getSundayDate() + ":\n";
        for(OrderLineDto o : order.getOrderLinesDtos()) {
            message += String.valueOf(o.getUnits()) + " - " + o.getItem() + "\n";
        }
        message += "\nPor un total de " + order.getTotalPrice() + " Euros !";

        return message;
    }

    public static String orderNotFoundMessage() {

        return "Non se atopou ningún pedido para esta semana.";
    }
    public static String lastBillMessage() {

        return "Envíoche a túa última factura.";

    }

    public static String lastBillNotFoundMessage() {
        return "Non se atopou o teu último recibo. Desculpa as molestias e se hai algún error contacta coa administración.";
    }


    public static String lastUnpaidBillMessage(ReceiptDto receiptDto) {
        String message = "";

        if(Objects.isNull(receiptDto)) {
            message += "Non tes ningún recibo pendente de pago!";
        } else {

            message += "O último recibo sen pagar que consta é do día " + receiptDto.getBillDto().getDate() +
                    ", cun importe total de: " + receiptDto.getPrice().toString() + " euros.";
        }

        return message;
    }

    public static String lastUnpaidBillNotFoundMessage() {
        return "Non se atopou ningún recibo sen pagar.";
    }

    public static String userNotConnectedMessage(String name) {

        return "Hola " + name + ", sentímolo pero o teu usuario aínda non está dado de alta no sistema. Nos próximos días configurarase o teu usuario e xa poderáss facer uso das opcións que ofrece ZocaminhocaBot. Disculpa as molestias. =(";
    }

    public static String operationProblems () {
        return "Tivemos un problema realizando a operación. Desculpa as molestias e contacta coa administración se persiste.";
    }

    public static String unknownOperation () {
        return "Desculpa as molestias pero non recoñezo esta operación, utiliza o teclado personalizado. Graciñas !!!";
    }

    public static String serverError() {
        return "Desculpa as molestias pero estamos a ter problemas cos servidores. Esperamos resolver pronto esta incidencia.";
    }

}
