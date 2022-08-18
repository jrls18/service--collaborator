package br.com.developcorporation.collaborator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagination<T> implements Serializable {

    private static final long serialVersionUID = 4579381568411939130L;

    private List<T> items;
    private Integer totalPages;
    private Integer currentPages;
    private Long totalItems;
}
