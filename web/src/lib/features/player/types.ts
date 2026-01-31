export interface MatterSnapshot {
  id: string;
  name: string;
  state: string;
  temperature: number;
}

export interface ContainerSnapshot extends MatterSnapshot {
  contents: MatterSnapshot[];
  isHeated: boolean;
  intensity?: Intensity | null;
}

export enum InstructionStepCategory {
  Add,
  Heat,
  Mix,
  Wait,
  Transfer,
  Done,
}

export enum Intensity {
  Low,
  Medium,
  High,
}

export interface InstructionStep {
  category: InstructionStepCategory;
  description: string;
  intensity?: Intensity | null;
  durationSeconds?: number | null;
}

export interface PlayerAction {
  instruction: InstructionStep;
  containerName: string;
  snapshots: ContainerSnapshot[];
}
