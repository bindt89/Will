package com.will.wallet;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.MnemonicUtils;


import org.web3j.crypto.Bip32ECKeyPair;
public class UserEthWallet {

	
	
	public static void main22(String[] args) {
		
	}
	public static void main(String[] args) {
		
		String password = "69584";
		String mnemonic = "among adult sock culture steel dream deer dutch pass length vehicle dial";
		
		Bip32ECKeyPair masterKeypair = Bip32ECKeyPair.generateKeyPair(MnemonicUtils.generateSeed(mnemonic, null));
		
		
		
		
		//m/44'/60'/0'/0 derivation path
		int[] derivationPath_user0 = {44 | Bip32ECKeyPair.HARDENED_BIT, 60 | Bip32ECKeyPair.HARDENED_BIT, 0 | Bip32ECKeyPair.HARDENED_BIT, 0,0};
		int[] derivationPath_user1 = {44 | Bip32ECKeyPair.HARDENED_BIT, 60 | Bip32ECKeyPair.HARDENED_BIT, 0 | Bip32ECKeyPair.HARDENED_BIT, 0,1};
		int[] derivationPath_user2 = {44 | Bip32ECKeyPair.HARDENED_BIT, 60 | Bip32ECKeyPair.HARDENED_BIT, 0 | Bip32ECKeyPair.HARDENED_BIT, 0,2};
	
		// Generate a BIP32 master keypair from the mnemonic phrase
		
		

		Bip32ECKeyPair  derivedKeyPair0 = Bip32ECKeyPair.deriveKeyPair(masterKeypair, derivationPath_user0);
		Bip32ECKeyPair  derivedKeyPair1 = Bip32ECKeyPair.deriveKeyPair(masterKeypair, derivationPath_user1);
		Bip32ECKeyPair  derivedKeyPair2 = Bip32ECKeyPair.deriveKeyPair(masterKeypair, derivationPath_user2);
		

		// Load the wallet for the derived keypair
		Credentials credentials0 = Credentials.create(derivedKeyPair0);
		Credentials credentials1 = Credentials.create(derivedKeyPair1);
		Credentials credentials2 = Credentials.create(derivedKeyPair2);
		
		
		System.out.println("dddd");
		
	}
}