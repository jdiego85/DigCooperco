package ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils;

import ec.fin.cooperco.aplicacionesescritorio.digcooperco.dto.Mensajes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class MessageUtils {
    private static List<VariableEnum> variablesRegister
            = Arrays.asList(VariableEnum.values());
    @Value("${messages.config_info}")
    private String configInfoMessage;
    @Value("${messages.user_not_exist}")
    private String userNotExistMessage;
    @Value("${messages.update_version}")
    private String updateVersionMessage;
    @Value("${messages.start_session}")
    private String startSessionMessage;
    @Value("${messages.user_find}")
    private String userFindMessage;
    @Value("${messages.user_login}")
    private String userLoginMessage;
    @Value("${messages.user_login_error}")
    private String userLoginErrorMessage;
    @Value("${messages.user_login_password_error}")
    private String userLoginPasswordErrorMessage;
    @Value("${messages.file_general_error}")
    private String fileGeneralErrorMessage;
    @Value("${messages.service_error}")
    private String serviceErrorMessage;

    //Statics
    public static String configInfoMessageStatic;
    public static String userNotExistMessageStatic;
    public static String updateVersionMessageStatic;
    public static String startSessionMessageStatic;
    public static String userFindMessageStatic;
    public static String userLoginMessageStatic;
    public static String userLoginErrorMessageStatic;
    public static String userLoginPasswordErrorMessageStatic;
    public static String fileGeneralErrorMessageStatic;
    public static String serviceErrorMessageStatic;

    @Value("${messages.config_info}")
    public void setConfigInfoMessageStatic(String value) {
        MessageUtils.configInfoMessageStatic = value;
    }

    @Value("${messages.user_not_exist}")
    public void setUserNotExistMessageStatic(String value) {
        MessageUtils.userNotExistMessageStatic = value;
    }

    @Value("${messages.update_version}")
    public void setUpdateVersionMessageStatic(String value) {
        MessageUtils.updateVersionMessageStatic = value;
    }

    @Value("${messages.start_session}")
    public void setStartSessionMessageStatic(String value) {
        MessageUtils.startSessionMessageStatic = value;
    }

    @Value("${messages.user_find}")
    public void setUserFindStatic(String value) {
        MessageUtils.userFindMessageStatic = value;
    }
    @Value("${messages.user_login}")
    public void setUserLoginMessageStatic(String value) {
        MessageUtils.userLoginMessageStatic = value;
    }
    @Value("${messages.user_login_error}")
    public void setUserLoginErrorMessageStatic(String value) {
        MessageUtils.userLoginErrorMessageStatic = value;
    }
    @Value("${messages.user_login_password_error}")
    public void setUserLoginPasswordErrorMessageStatic(String value) {
        MessageUtils.userLoginPasswordErrorMessageStatic = value;
    }
    @Value("${messages.file_general_error}")
    public void setFileGeneralErrorMessageStatic(String value) {
        MessageUtils.fileGeneralErrorMessageStatic = value;
    }
    @Value("${messages.service_error}")
    public void setServiceErrorMessageStatic(String value) {
        MessageUtils.serviceErrorMessageStatic = value;
    }

    private static String replaceVariable(String variable, String value, String message) {
        return message.replace("{{" + variable + "}}", (value != null ? value : ""));
    }

    public static Mensajes replaceUsername(String value, String message) {
        return new Mensajes(replaceVariable(VariableEnum.username.toString(), value, message));
    }

    public static Mensajes replaceVersion(String value, String message) {
        return new Mensajes(replaceVariable(VariableEnum.version.toString(), value, message));
    }


}
