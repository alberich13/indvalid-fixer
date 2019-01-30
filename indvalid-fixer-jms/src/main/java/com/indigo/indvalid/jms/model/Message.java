package com.indigo.indvalid.jms.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message implements Serializable {
	private static final long serialVersionUID = -8095077935188702303L;
	
	@ApiModelProperty(example = "10", notes = "Identificador de mensaje")
	private Integer id;
	@ApiModelProperty(example = "Esto es un mensaje", notes = "Cadena con el cuepo del mensaje")
	private String body;
	
	@ApiModelProperty(example = "2019-01-01@00:00:00", notes = "Fecha de envío de mensaje")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
	private LocalDateTime date;
}
