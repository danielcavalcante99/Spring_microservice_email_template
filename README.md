# Spring email template |SMTP GMAIL
![image](https://user-images.githubusercontent.com/74054701/147880935-fae18e2e-4ab7-4cef-9a98-94fecb05b366.png)


## 1 - Definindo a Estrutura do Projeto

**Projeto:** Maven

**Depedências:**
- Spring Web;
- Java Mail Sender;
- Validation;
- Lombok;
- thymeleaf.

**Copie e cole no arquivo pow.xml**
~~~
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-mail</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
              <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
~~~    

#
#

## 2 - Definindo Properties

- **O servidor SMTP ao qual se conectar:**
~~~
spring.mail.host=smtp.gmail.com
~~~
- **A porta do servidor SMTP para conexão:**
~~~ 
spring.mail.port=587  
~~~
- **Nome de usuário padrão para SMTP:**
~~~ 
spring.mail.username=******@gmail.com  
~~~
- **Insira a senha(não é a senha tradicional)** </br>
Segue o passo a passo nesse link para gerar essa senha de 16 dígitos: https://support.google.com/accounts/a...
~~~ 
spring.mail.password=****************
~~~ 
- **Se verdadeiro, tente autenticar o usuário usando o comando AUTH. O padrão é falso:**
~~~ 
spring.mail.properties.mail.smtp.auth=true 
~~~ 
- **Se verdadeiro, habilita o uso do STARTTLS comando (se compatível com o servidor) para alternar a conexão para uma conexão protegida por TLS antes de emitir qualquer comando de login. Se o servidor não suportar STARTTLS, a conexão continuará sem o uso de TLS.**

O **Transport Layer Security (TLS)** é um protocolo de **segurança** que **criptografa e-mails** para garantir a segurança. O **TLS** impede o acesso não autorizado ao e-mail quando ele está em trânsito por conexões de Internet.

Por padrão, o **Gmail** sempre tenta usar o **TLS ao enviar e-mails**. No entanto, uma conexão segura TLS exige que o remetente e o destinatário **usem esse protocolo**. Se o servidor de recebimento **não usar o TLS**, o **Gmail ainda entregará a mensagem, mas a conexão não será segura**. 
~~~ 
spring.mail.properties.mail.smtp.starttls.enable=true 
~~~

fonte: https://javaee.github.io/javamail/docs/api/com/sun/mail/smtp/package-summary.html


**Copie e Cole no arquivo application.properties:**
~~~
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=***********@gmail.com
spring.mail.password=****************
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
~~~

#
#
## 3 - implementando a camada config

**Package:** config;

**Enum:** SpringMailConfig

![image](https://user-images.githubusercontent.com/74054701/148135665-1676e25f-6305-4bfd-999d-93c2db0daf21.png)

- **Classe: SpringResourceTemplateResolver:** usaremos para definir a configuração da **localização da pasta**, do **tipo**, do **modo** e da **codificação de caracteres** do **template do email**.

- **Classe: SpringTemplateEngine:** iremos usar o método **addTemplateResolver** para **adicionar a configuração do template do email**.

- **@Configuration:** Indica que uma classe declara um ou mais **@Bean**.

- **@Bean:** quando usamos a anotação **@Bean** em um método estamos passando a **responsabilidade** para o **Spring** está **gerenciando o objeto** desse método, como por exemplo fazer a **instanciação** e deixar outras classes utilizarem ele como **depedência**. **@Bean** são criados dentro de classes do tipo **@Configuration**.

#
#
## 4 - implementando a camada model

**Package:** model.enums;

**Enum:** StatusEmail

![image](https://user-images.githubusercontent.com/74054701/147883934-2687b83a-f55c-472d-831b-1bb0cccd5707.png)

**Package:** model;

**Class:** EmailTemplateModel

![image](https://user-images.githubusercontent.com/74054701/148126318-c83b2040-750a-4941-aba5-78efc685df9d.png)

**@Data:** é como ter implícitos **@Getter**, **@Setter**, **@ToString**, **@EqualsAndHashCodee** e **@RequiredArgsConstructor**.

**@NotBlank:** para **não permitir** valores **nulos** ou **vazios**.

**@Email:** verifica se o que foi digitado no campo contém **características de email**.

#
#
## 5 - Implementando a camada de serviço

**Package:** services;

**Class:** EmailService

![image](https://user-images.githubusercontent.com/74054701/148126448-b97267e2-aa93-4a83-9b9a-3b68f4dd970f.png)

- **@Service:** anotação para classe da camada de serviço, onde estão a regra de negócio. Essa anotação serve como uma especialização de **@Component**.

- **@Autowired:** usado para injeção de depedência.

 - **Classe MimeMessage:** para criar novas **mensagens** de estilo **MIME(Multipurpose Internet Mail Extensions)**, que se refere a um padrão da internet para o formato das mensagens de **correio eletrônico**. Ele pode ser utilizado para incluir **vários tipos de conteúdo dentro de uma única mensagem**.

- **Classe MimeMessageHelper:** auxiliar para a criação de mensagens MIME. Ele oferece suporte para imagens, anexos de e-mail típicos e conteúdo de texto em um layout HTML.
**MimeMessageHelper**(**MimeMessage** mimeMessage, **boolean** multipart, **String** enconding);
			 
  **1ª Param mimeMessage:** iremos passar para o parâmetro um **objeto** do tipo **MimeMessage**. 

  **2ª Param multipart:** tipo **boleano(true / false)** se **verdadeiro** permite que seu email seja 
  composto além dos textos, com **arquivos em anexos** e com **HTML** para formatação de texto 
  ou seja várias partes **misturadas(multipart)**.

  **3ª Param encoding:** tipo **String** e passamos para o parâmetro algum tipo de **codificação de caracteres**.

- **Classe JavaMailSender:** irá nos fornecer um método chamado **createMimeMessage** para criar um objeto do tipo **MimeMessage** e também o método **send** que aceita o **tipo MimeMessage** para o envio de email.

- **Classe SpringTemplateEngine:** usaremos o seu método **process** para enviar os **atributos** que definirmos para o arquivo **HTML**.

#
#
## 6 - Implementando a camada de controler

**Package:** controllers;

**Class:** EmailController

![image](https://user-images.githubusercontent.com/74054701/148134994-7ee4a891-4770-4aed-b81b-475c1f5b979d.png)


- **@RestController:** é para marcar que o controlador está fornecendo serviços REST com o tipo de resposta JSON,  é composição das
anotações **@Controller** e **@ResponseBody**.

- **@RequestMapping:** usamos aqui para definir uma **rota inicial** para realizar as **requisições**.

- **@PostMapping:** usamos para definir uma **rota** para a **requisição** do **tipo POST**.

- **@RequestBody:** uma anotação indicando que **um parâmetro** de método deve ser **associado** ao **corpo da solicitação HTTP**.

- **@Valid:** irá verificar se todos os campos foram **preenchidos corretamente**, respeitando as **regras** das anotações **@NotBlank**, **@Email**, **@NotEmpty** e etc... 

- **Classe Context(package: org.thymeleaf.context.Context):** iremos usar para **adicionar atributos** que terão sua **chave** e seu **valor**.

- **Classe ResponseEntity:** representa toda a **resposta HTTP:** **código de status**, **cabeçalhos** e **corpo**. Podemos usa-la para **configurar** totalmente a **resposta HTTP**.

#
#
## 7 - Construir o template

- **Pasta:** resources/templates/email-templates

- **Arquivo HTML:** template-thymeleaf.html

![image](https://user-images.githubusercontent.com/74054701/148138935-fd838208-7dc4-4634-b7c5-04f7f2978505.png)

- **Pasta das imagens:** resources/templates/img-templates

![image](https://user-images.githubusercontent.com/74054701/148139151-577e90b5-65b7-4896-8328-8eefe68b8442.png)

#
#
## 8 - Enviar o email

- **Requisição:** Post

- **URL da requisição:** http://localhost:8080/email/sendTemplateEmail

![image](https://user-images.githubusercontent.com/74054701/148138495-3478ecbb-6ce4-4faa-a209-6ca0c3438f00.png)
