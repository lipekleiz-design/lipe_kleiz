package com.lipe_kleiz.delivery_api.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lipe_kleiz.delivery_api.model.Cliente;
import com.lipe_kleiz.delivery_api.repository.ClienteRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepository;

    public DataLoader(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) {

        System.out.println("\n=================================");
        System.out.println("INICIANDO CARGA DE DADOS");
        System.out.println("=================================\n");

        clienteRepository.deleteAll();

        inserirClientes();

        testarConsultas();

        System.out.println("\n=================================");
        System.out.println("CARGA DE DADOS CONCLUÍDA");
        System.out.println("=================================\n");
    }

    private void inserirClientes() {

        System.out.println("--- Inserindo Clientes ---");

        Cliente cliente1 = new Cliente();
        cliente1.setNome("João Silva");
        cliente1.setEmail("joao@email.com");
        cliente1.setAtivo(true);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Maria Santos");
        cliente2.setEmail("maria@email.com");
        cliente2.setAtivo(true);

        Cliente cliente3 = new Cliente();
        cliente3.setNome("Pedro Oliveira");
        cliente3.setEmail("pedro@email.com");
        cliente3.setAtivo(false);

        clienteRepository.saveAll(
            Arrays.asList(cliente1, cliente2, cliente3)
        );

        System.out.println("✓ 3 clientes inseridos");
    }

    private void testarConsultas() {

        System.out.println("\n=== TESTANDO CONSULTAS ===");

        var clientePorEmail =
                clienteRepository.findByEmail("joao@email.com");

        System.out.println(
            "Cliente por email: "
            + clientePorEmail
                .map(Cliente::getNome)
                .orElse("Não encontrado")
        );

        var clientesAtivos =
                clienteRepository.findByAtivoTrue();

        System.out.println(
            "Clientes ativos: " + clientesAtivos.size()
        );

        var clientesPorNome =
                clienteRepository.findByNomeContainingIgnoreCase("silva");

        System.out.println(
            "Clientes contendo 'silva': "
            + clientesPorNome.size()
        );

        boolean emailExiste =
                clienteRepository.existsByEmail("maria@email.com");

        System.out.println(
            "Email maria@email.com existe: "
            + emailExiste
        );

        Long totalAtivos =
                clienteRepository.countClientesAtivos();

        System.out.println(
            "Total de clientes ativos: "
            + totalAtivos
        );
    }
}