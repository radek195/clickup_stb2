package pl.akademiaqa.DTO.request;

import lombok.Data;

@Data
public class UpdateTaskRequestDTO {
    private String name;
    private String description;
    private String status;
    private String priority;
    private String parent;
    private String time_estimate;
    private String assignees;
    private Boolean archived;
}
