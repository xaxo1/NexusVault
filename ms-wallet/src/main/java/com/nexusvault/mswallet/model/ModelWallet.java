package com.nexusvault.mswallet.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * [IMPORTANTE - ARQUITECTURA DE MICROSERVICIOS]
     * No usamos @OneToOne apuntando a ModelUser porque están en bases de datos distintas.
     * Guardamos el 'user_id' como un número simple. Cuando MS-Orders necesite cobrar,
     * buscará la billetera que coincida con este número.
     * 'unique = true' asegura que un jugador jamás pueda tener dos billeteras en el sistema.
     */
    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    /*
     * [CRÍTICO - MANEJO FINANCIERO]
     * NUNCA usamos 'float' o 'double' para dinero, porque Java comete errores de redondeo
     * en los decimales (ej: 0.1 + 0.2 = 0.30000000000000004).
     * Usamos 'BigDecimal' que garantiza precisión absoluta en las transacciones.
     * 'precision = 10' -> Permite hasta 10 dígitos en total (ej: 99.999.999,00)
     * 'scale = 2'      -> Obliga a tener exactamente 2 decimales para los centavos.
     */
    @Column(name = "saldo_actual", nullable = false, precision = 10, scale = 2)
    private BigDecimal saldoActual;

    @Column(name = "cuenta_bancaria_vinculada", length = 100)
    private String cuentaBancariaVinculada;

    /*
     * [AUDITORÍA]
     * 'updatable = false' bloquea esta columna a nivel de base de datos.
     * Si un hacker o un error en el código intenta hacer un UPDATE para cambiar
     * la fecha de creación de la billetera, MySQL lo rechazará automáticamente.
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    /*
     * [TRIGGER DE BASE DE DATOS - INICIALIZACIÓN]
     * @PrePersist es un interceptor. Spring Boot pausa milisegundos ANTES de ejecutar
     * el 'INSERT' en MySQL y ejecuta este bloque.
     * Esto nos garantiza que los datos por defecto (como saldo en cero) siempre
     * se asignen, incluso si al Frontend se le olvidó enviarlos.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        // BigDecimal.ZERO es una constante de Java más eficiente que escribir 'new BigDecimal("0")'
        if (this.saldoActual == null) {
            this.saldoActual = BigDecimal.ZERO;
        }
        if (this.isActive == null) {
            this.isActive = true;
        }
    }

    /*
     * [TRIGGER DE BASE DE DATOS - TRAZABILIDAD]
     * @PreUpdate actúa ANTES de un 'UPDATE' en MySQL.
     * Cada vez que sumemos o restemos saldo, este método actualizará la fecha
     * automáticamente sin que tengamos que hacerlo manual en el Service.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}