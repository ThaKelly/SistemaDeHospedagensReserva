package gerenciamento;

import java.util.List;
import java.util.Scanner;
import java.io.IOException;

import repositorio.RepositorioDoHotel;
import entidades.Cliente;
import entidades.Funcionario;
import entidades.Hospedagem;
import entidades.Quarto;
import entidades.Reserva;
import entidades.Cabana;
import entidades.Apartamento;
import entidades.ServicoAdicional;
import exception.ReservaNaoEncontradaException;
import exception.CancelamentoForaDoPrazoException;
import exception.CheckInInvalidoException;
import exception.CheckOutInvalidoException;
import exception.HospedagemIndisponivelException;
import exception.ServicoNaoPermitidoException;


public class Terminal {

      private CadastroDeClientes cadastroDeClientes;
      private CadastroDeFuncionarios cadastroDeFuncionarios;
      private GerenciamentoDeHospedagens gerenciamentoDeHospedagens;
      private GerenciamentoDeReservas gerenciamentoDeReservas;
      private GerenciamentoDeServicoAdicional gerenciamentoDeServicoAdicional;
      
      public Terminal(RepositorioDoHotel repositorio) {
          this.cadastroDeClientes = new CadastroDeClientes(repositorio);
          this.cadastroDeFuncionarios = new CadastroDeFuncionarios(repositorio);
          this.gerenciamentoDeHospedagens = new GerenciamentoDeHospedagens(repositorio);
          this.gerenciamentoDeReservas = new GerenciamentoDeReservas(repositorio);
          this.gerenciamentoDeServicoAdicional = new GerenciamentoDeServicoAdicional(repositorio);
      }
      
      public void iniciaOperacao() {
  		int opcao;
  		opcao = this.getOpcao();
  		while (opcao != 8) { 		
  			switch (opcao) {
  			case 1:
  				//cadastrar cliente
  				Funcionario funcionario = cadastroDeFuncionarios.buscarFuncionarioPorId(this.getString("Id do funcionário"));
  			    if (funcionario == null) {
  			        System.out.println("Funcionário não encontrado. Apenas funcionários podem cadastrar clientes.");
  			        break;
  			    }
  			   boolean b = cadastroDeClientes.cadastrarCliente(getString("Digite o nome do cliente"),
  					   getString("Digite o CPF do cliente"),getString("digite o telefone"));
  			   if(b) {
  				 System.out.println("Cliente cadastrado com sucesso!"); 				
  			   }else {
  				 System.out.println("Cliente já está cadastrado.");   
  			   }
  			 break; 
  			    
  			case 2:
  				//cadastrar funcionário
  			  boolean c = cadastroDeFuncionarios.cadastrarFuncionario(getString("id do Funcionario"),
  					  getString("nome Funcionario"), getString("cargo do Funcionario"));
              if (c) {
                  System.out.println("Funcionário cadastrado com sucesso!");                
              } else {
                  System.out.println("Funcionário já está cadastrado.");
              }
              break;
              
  			case 3: 
  				//cadastrar hospedagens no sistema
  				int op = getInt("Deseja cadastrar 1-quarto, 2-cabana, 3-apartamento");
  				Hospedagem novaHospedagem = null;
  				
  				do {
  				switch (op) {
  				case 1:
  					novaHospedagem = new Quarto(getString("digite o Id"), (double)getInt("Digite o preço da diária"));
  					boolean a = gerenciamentoDeHospedagens.cadastrarHospedagem(novaHospedagem);
  					if(a) {
  	  					System.out.println("Hospedagem Cadastrada com sucesso!");
  	  				}else {
  	  					System.out.println("Essa hospedagem já está cadastrada");
  	  				}  	
  					break;
  				case 2:
  					novaHospedagem = new Cabana (getString("Digite o Id"), getInt("Digite a capacidade màxima"),
  							(double)getInt("Digite o preço da diària"),
  							(double)getInt("digite a taxa da temporada"));
  					boolean d = gerenciamentoDeHospedagens.cadastrarHospedagem(novaHospedagem);
  					if(d) {
  	  					System.out.println("Hospedagem Cadastrada com sucesso!");
  	  				}else {
  	  					System.out.println("Essa hospedagem já está cadastrada");
  	  				}  	
  					break;
  				case 3:
  					novaHospedagem = new Apartamento(getString("Digite o Id"),getInt("Digite a capacidade màxima"),
  							(double)getInt("Digite o preço da diária"));
  					boolean e = gerenciamentoDeHospedagens.cadastrarHospedagem(novaHospedagem);
  					if(e) {
  	  					System.out.println("Hospedagem Cadastrada com sucesso!");
  	  				}else {
  	  					System.out.println("Essa hospedagem já está cadastrada");
  	  				}  
  					break;
  				default:
  		            System.out.println("Opção inválida. Tente novamente.");
  		            break;
  				}
  				} while (op < 1 || op > 3);  					
  			break;
  				
  			case 4:
  				 boolean f = gerenciamentoDeServicoAdicional.cadastrarServicoAdicional(getString("id do serviço"), 
  						 getString(" descrição do serviço"),(double)getInt("preço do serviço"));
  	              if (f) {
  	                  System.out.println("Serviço cadastrado com sucesso!");
  	              } else {
  	                  System.out.println("Serviço já está cadastrado.");
  	              }
  	        break;
  	        
  			case 5:
  				// reservar hospedagem ou serviço adicional
  			    String cpfCliente = getString("Digite o CPF do cliente");
  			    Cliente cliente = cadastroDeClientes.buscarClientePorCpf(cpfCliente);
  			    
  			    if (cliente == null) {
  			        System.out.println("Cliente não encontrado. Cadastre o cliente antes de realizar uma reserva.");
  			        break;
  			    }

  			    int tipoReserva = getInt("Deseja reservar 1-Hospedagem ou 2-Serviço Adicional?");
  			    
  			    if (tipoReserva == 1) {
  			        String idHospedagem = getString("Digite o ID da hospedagem que deseja reservar");
  			        Hospedagem hospedagem = gerenciamentoDeHospedagens.buscarHospedagemPorId(idHospedagem);
  			        
  			        if (hospedagem == null) {
  			            System.out.println("Hospedagem não encontrada.");
  			            break;
  			        }

  			        String idReserva = getString("Digite o um ID para a reserva");
  			        try {
  			            gerenciamentoDeReservas.realizarReserva(cliente, hospedagem, idReserva);
  			            System.out.println("Reserva de hospedagem realizada com sucesso.");
  			        } catch (HospedagemIndisponivelException e) {
  			            System.out.println(e.getMessage());
  			        }
  			        
  			    } else if (tipoReserva == 2) {
  			        String idServico = getString("Digite o ID do serviço adicional que deseja reservar");
  			        ServicoAdicional servico = gerenciamentoDeServicoAdicional.buscarServicoAdicionalPorId(idServico);
  			        
  			        if (servico == null) {
  			            System.out.println("Serviço adicional não encontrado.");
  			            break;
  			        }

  			        try {
  			            gerenciamentoDeReservas.adicionarServicoAdicional(cliente, servico);
  			            System.out.println("Serviço adicional reservado com sucesso.");
  			        } catch (ReservaNaoEncontradaException | ServicoNaoPermitidoException e) {
  			            System.out.println(e.getMessage());
  			        }
  			        
  			    } else {
  			        System.out.println("Opção inválida.");
  			    }
  			    break;


            case 6: 
            	// disponibilidade de hospedagens e serviços
                System.out.println("=== Hospedagens Disponíveis ===");
                List<Hospedagem> hospedagensDisponiveis = gerenciamentoDeHospedagens.listarHospedagensDisponiveis();
                if (hospedagensDisponiveis.isEmpty()) {
                    System.out.println("Nenhuma hospedagem disponível no momento.");
                } else {
                	for (Hospedagem hospedagem : hospedagensDisponiveis) {
                        String tipo = getTipoHospedagem(hospedagem);
                        System.out.println("ID: " + hospedagem.getId() + " | Tipo: " + tipo + " | Preço: " + hospedagem.getPrecoDiaria());
                    }
               
                }
                System.out.println("\n=== Serviços Adicionais Disponíveis ===");
                List<ServicoAdicional> servicosDisponiveis = gerenciamentoDeServicoAdicional.listarServicosDisponiveis();
                if (servicosDisponiveis.isEmpty()) {
                    System.out.println("Nenhum serviço adicional disponível no momento.");
                } else {
                    for (ServicoAdicional servico : servicosDisponiveis) {
                        System.out.println("ID: " + servico.getId() + " | Descrição: " + servico.getDescricao() + " | Preço: " + servico.getPreco());
                    }
                System.out.println("\n=====================================");
                }
                break;
                
            case 7:
                // Cancelar reserva                      
                String idReservaCancelar = getString("Digite o ID da reserva que deseja cancelar");
                String motivoCancelamento = getString("Digite o motivo do cancelamento");
                try {
                    boolean cancelada = gerenciamentoDeReservas.cancelarReserva(idReservaCancelar, motivoCancelamento);
                    if (cancelada) {
                        System.out.println("Reserva cancelada com sucesso.");
                    }
                } catch (ReservaNaoEncontradaException | CancelamentoForaDoPrazoException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 8:
            	//fazer check in
                String idReservaCheckIn = getString("Digite o ID da reserva para check-in");
                try {
                    gerenciamentoDeReservas.realizarCheckIn(idReservaCheckIn);
                    System.out.println("Check-in realizado com sucesso ");
                } catch (ReservaNaoEncontradaException | CheckInInvalidoException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 9:
            	String idReservaCheckOut = getString("Digite o ID da reserva para check-out");
                try {
                    gerenciamentoDeReservas.realizarCheckOut(idReservaCheckOut);
                    System.out.println("Check-out realizado com sucesso ");
                } catch (ReservaNaoEncontradaException | CheckOutInvalidoException e) {
                    System.out.println(e.getMessage());
                }
                break;
                
            case 10: 
            	//relatórios
                System.out.println("=== Relatórios ===");
                System.out.println("1 - Relatório de Reservas Ativas");
                System.out.println("2 - Relatório de Ocupação");
                System.out.println("3 - Relatório de Cancelamentos");
                System.out.println("4 - Relatório Financeiro");
                System.out.println("5 - Histórico de Cliente");
                int opcaoRelatorio = getInt("Escolha uma opção de relatório");

                switch (opcaoRelatorio) {
                    case 1:
                        // Relatório de Reservas Ativas
                        System.out.println("=== Relatório de Reservas Ativas ===");
                        List<Reserva> reservasAtivas = gerenciamentoDeReservas.gerarRelatorioReservasAtivas();
                        if (reservasAtivas.isEmpty()) {
                            System.out.println("Nenhuma reserva ativa no momento.");
                        } else {
                            for (Reserva reserva : reservasAtivas) {
                                System.out.println("ID: " + reserva.getId() + " | Cliente: " + reserva.getCliente().getNome()
                                        + " | Hospedagem: " + reserva.getHospedagem().getId()
                                        + " | Check-in: " + reserva.getDataCheckIn()
                                        + " | Check-out: " + reserva.getDataCheckOut());
                            }
                        }
                        break;

                    case 2:
                        // Relatório de Ocupação
                        System.out.println("=== Relatório de Ocupação ===");
                        double taxaOcupacao = gerenciamentoDeReservas.calcularTaxaOcupacao();
                        System.out.println("Taxa de ocupação: " + taxaOcupacao + "%");
                        break;

                    case 3:
                        // Relatório de Cancelamentos
                        System.out.println("=== Relatório de Cancelamentos ===");
                        List<Reserva> cancelamentos = gerenciamentoDeReservas.gerarRelatorioCancelamentos();
                        if (cancelamentos.isEmpty()) {
                            System.out.println("Nenhum cancelamento registrado.");
                        } else {
                            for (Reserva reserva : cancelamentos) {
                                System.out.println("ID: " + reserva.getId() + " | Cliente: " + reserva.getCliente().getNome()
                                        + " | Motivo: " + reserva.getMotivoCancelamento());
                            }
                        }
                        break;

                    case 4:
                        // Relatório Financeiro
                        System.out.println("=== Relatório Financeiro ===");
                        double faturamentoTotal = gerenciamentoDeReservas.calcularFaturamentoTotal();
                        System.out.println("Faturamento total: R$ " + faturamentoTotal);
                        break;

                    case 5:
                        // Histórico de Cliente
                        String cpfCli = getString("Digite o CPF do cliente");
                        List<Reserva> historicoCliente = gerenciamentoDeReservas.gerarHistoricoCliente(cpfCli);
                      if (historicoCliente.isEmpty()) {
                            System.out.println("Nenhuma reserva encontrada para o cliente.");
                        } else {
                            System.out.println("=== Histórico de Cliente ===");
                            for (Reserva reserva : historicoCliente) {
                                System.out.println("ID: " + reserva.getId() + " | Hospedagem: " + reserva.getHospedagem().getId()
                                        + " | Check-in: " + reserva.getDataCheckIn()
                                        + " | Check-out: " + reserva.getDataCheckOut()
                                        + " | Status: " + reserva.getStatus());
                           }
                        }
                      }
                     break;
            case 11:
            	System.out.println("Saindo...");
            	break;     
                 
           default:
              System.out.println("Opção inválida. Tente novamente.");
              break;
  			}
      opcao = this.getOpcao();       			 		  			
  	 
  	    } 
      }
      private String getTipoHospedagem(Hospedagem hospedagem) {
    	    if (hospedagem instanceof Quarto) {
    	        return "Quarto";
    	    } else if (hospedagem instanceof Cabana) {
    	        return "Cabana";
    	    } else if (hospedagem instanceof Apartamento) {
    	        return "Apartamento";
    	    } else {
    	        return "Tipo desconhecido";
    	    }
     }
      
     
      private int getOpcao() {
  		int opcao;
  		do {
  			opcao = getInt("Opcao:\n 1 - Cadastrar cliente,\n 2 - Cadastrar Funcionario,"
  			+ "\n 3 - Cadastro de hospedagens, \n 4- Cadastro de serviços adicionais,"
  			+ "\n 5 -Reservar hospedagems ou serviços adicionais, "
  			+ "\n 6 - Disponibilidade de hospedagem e serviços, \n 7 -Cancelar reserva,"
  			+ " \n 8 - Realização de check-in ,\n 9 - Realização de check-out e fechamento da conta,\n 10- geração de relatórios"
  			+ "\n 11 - sair");
  				if (opcao != 1 & opcao != 2 & opcao != 3 & opcao != 4 & opcao != 5 & opcao != 6 & opcao!= 7
  					& opcao != 8 & opcao != 9 & opcao!=10 & opcao!=11) {
  					opcao = 0;
  				}	
  		} while (opcao == 0);
  		return opcao;
      }
      private String getString(String string) {
          System.out.println("" + string);
          Scanner scanner = new Scanner(System.in);
          return scanner.nextLine();
      }
      private int getInt(String string) {
  		Scanner r = new Scanner(System.in);
  		System.out.println("" + string);
  		if (r.hasNextInt()) {
  			return r.nextInt();
  		}
  		String st = r.next();
  		System.out.println("Erro na Leitura de Dados");
  		return 0;
  	}
      
      
      
      
}
