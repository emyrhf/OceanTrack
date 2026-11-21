package controller;

import entity.EspecieMarinha;
public class EspecieMarinhaController {
    public void criarEspecieMarinha( String nomeComum, String nomeCientifico, String habitat, double pesoMedioKg, double tamanhoMedioCm, String statusConservacao) {
        int id = 0; 
        EspecieMarinha especie = new EspecieMarinha(id, nomeComum, nomeCientifico, habitat, pesoMedioKg, tamanhoMedioCm, statusConservacao);
    }
}
