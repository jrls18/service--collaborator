package br.com.group.developer.corporation.service.collaborator.core.validation;

import br.com.group.developer.corporation.service.collaborator.domain.constants.MessageDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.exception.CollaboratorErrorValidatorException;
import br.com.grupo.developer.corporation.libcommons.enums.CoreEnum;
import br.com.grupo.developer.corporation.libcommons.exception.DomainException;
import br.com.grupo.developer.corporation.libcommons.message.Message;
import br.com.grupo.developer.corporation.libcommons.utils.UseFul;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

interface BaseCoreValidator<T>{

    void add(final T value);

    void update(final T value);



    default void throwCollaboratorErrorValidatorException(final List<Message.Details> detailsList){
        if(Boolean.FALSE.equals(CollectionUtils.isEmpty(detailsList))){
            var items = UseFul.removeNullToList(detailsList);
            if(Boolean.FALSE.equals(items.isEmpty()))
                throw new CollaboratorErrorValidatorException(CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                        MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DO_COLABORADOR,items);
        }
    }

    default void throwDomainExceptionGeneric(final CoreEnum coreEnum, final String message, final List<Message.Details> detailsList) {
        if (Objects.nonNull(detailsList)) {
            List var4 = UseFul.removeNullToList(detailsList);
            if (!var4.isEmpty()) {
                throw new DomainException(coreEnum.getCode(), message, var4);
            }
        }

    }
}
